package entity;

import java.time.LocalDate;

import calcularsalario.DependenteException;
import calcularsalario.Parentesco;
import calcularsalario.Pessoa;

public class Dependente extends Pessoa {
	private Parentesco parentesco;

	public Dependente( String nome, String cpf, LocalDate dataNascimento, Parentesco parentesco) throws DependenteException {
		super( nome, cpf, dataNascimento);
		this.parentesco = parentesco;
		if (LocalDate.now().minusYears(18).isBefore(dataNascimento)) {

		} else {
			throw new DependenteException("Dependente deve ser menor que 18 anos!");
		}

	}

	public Parentesco getParentesco() {
		return parentesco;
	}

	public void setParentesco(Parentesco parentesco) {
		this.parentesco = parentesco;
	}
}