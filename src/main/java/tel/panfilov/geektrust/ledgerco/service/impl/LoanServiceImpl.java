package tel.panfilov.geektrust.ledgerco.service.impl;

import tel.panfilov.geektrust.ledgerco.model.Loan;
import tel.panfilov.geektrust.ledgerco.model.LoanId;
import tel.panfilov.geektrust.ledgerco.model.Payment;
import tel.panfilov.geektrust.ledgerco.model.vo.Balance;
import tel.panfilov.geektrust.ledgerco.model.vo.LoanConditions;
import tel.panfilov.geektrust.ledgerco.model.vo.PaymentSchedule;
import tel.panfilov.geektrust.ledgerco.repository.LoanRepository;
import tel.panfilov.geektrust.ledgerco.service.LoanService;
import tel.panfilov.geektrust.ledgerco.util.Assert;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.util.Optional;

public class LoanServiceImpl implements LoanService {

    public static final int MONTHS_PER_YEAR = 12;

    private final LoanRepository loanRepository;

    public LoanServiceImpl(LoanRepository loanRepository) {
        this.loanRepository = loanRepository;
    }

    @Override
    public Loan createLoan(LoanId loanId, BigDecimal principal, int term, BigDecimal interestRate) {
        Assert.isTrue(loanId != null, "Loan id is null");
        Assert.isTrue(principal != null, "Principal is null");
        Assert.isTrue(principal.signum() > 0, "Principal must be positive");
        Assert.isTrue(term > 0, "Term must be positive");
        Assert.isTrue(interestRate != null, "Interest rate is null");
        Assert.isTrue(interestRate.signum() > 0, "Interest rate must be positive");
        LoanConditions conditions = new LoanConditions(principal, term, interestRate);
        PaymentSchedule schedule = buildSchedule(principal, term, interestRate);
        Loan loan = new Loan(loanId, conditions, schedule);
        loanRepository.save(loan);
        return loan;
    }

    @Override
    public Loan addPayment(LoanId loanId, BigDecimal amount, int paymentNo) {
        Assert.isTrue(loanId != null, "Loan id is null");
        Assert.isTrue(amount != null, "Lump amount is null");
        Assert.isTrue(amount.signum() > 0, "Lump amount must be positive");
        Assert.isTrue(paymentNo >= 0, "Negative Payment No");
        Loan loan = getLoan(loanId);
        BigDecimal lumpSum = loan.getLumpSum(paymentNo);
        BigDecimal debt = loan.getSchedule().getTotal()
                .subtract(getPaidAmount(loan, lumpSum, paymentNo));
        Assert.isTrue(debt.compareTo(amount) >= 0, "Lump amount exceeds debt");
        loan.addPayment(new Payment(paymentNo, amount));
        loanRepository.update(loan);
        return loan;
    }

    protected BigDecimal getPaidAmount(Loan loan, BigDecimal lumpSum, int paymentNo) {
        PaymentSchedule schedule = loan.getSchedule();
        BigDecimal monthlyPayment = schedule.getMonthlyPayment();
        return monthlyPayment
                .multiply(new BigDecimal(paymentNo))
                .add(lumpSum)
                .min(schedule.getTotal());
    }

    @Override
    public Balance getBalance(LoanId loanId, int paymentNo) {
        Loan loan = getLoan(loanId);
        BigDecimal lumpSum = loan.getLumpSum(paymentNo);
        PaymentSchedule schedule = loan.getSchedule();
        BigDecimal monthlyPayment = schedule.getMonthlyPayment();
        int payments = Math.min(paymentNo, schedule.getMonths());
        BigDecimal paid = getPaidAmount(loan, lumpSum, payments);
        int remaining = schedule.getTotal()
                .subtract(paid)
                .divide(monthlyPayment, 0, RoundingMode.CEILING)
                .intValue();
        return new Balance(loanId, paid, remaining);
    }

    protected PaymentSchedule buildSchedule(BigDecimal principal, int term, BigDecimal interestRate) {
        BigDecimal total = principal
                .multiply(interestRate)
                .multiply(new BigDecimal(term))
                .divide(new BigDecimal(100), MathContext.UNLIMITED)
                .add(principal);
        int months = term * MONTHS_PER_YEAR;
        BigDecimal monthlyPayment = total.divide(new BigDecimal(months), 0, RoundingMode.CEILING);
        return new PaymentSchedule(total, months, monthlyPayment);
    }

    @Override
    public Optional<Loan> findLoan(LoanId loanId) {
        Assert.isTrue(loanId != null, "Loan id is null");
        return loanRepository.findById(loanId);
    }

}
