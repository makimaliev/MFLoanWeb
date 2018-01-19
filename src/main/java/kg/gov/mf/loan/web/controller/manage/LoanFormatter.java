package kg.gov.mf.loan.web.controller.manage;

import java.text.ParseException;
import java.util.Locale;

import org.springframework.format.Formatter;

import kg.gov.mf.loan.manage.model.loan.Loan;

public class LoanFormatter implements Formatter<Loan> {

	@Override
	public String print(Loan loan, Locale locale) {
		return String.valueOf(loan.getId());
	}

	@Override
	public Loan parse(String id, Locale locale) throws ParseException {
		Loan loan = new Loan();
		loan.setId(Long.parseLong(id));
		return loan;
	}

}
