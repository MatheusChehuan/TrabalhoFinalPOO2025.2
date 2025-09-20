package entity;

import java.time.LocalDate;

import calcularsalario.DependenteException;

public class Dependente extends Pessoa {
	private Parentesco parentesco;
	private int id_funcionario;

	public Dependente( String nome, String cpf, LocalDate dataNascimento, Parentesco parentesco, int id_funcionario) throws DependenteException {
		super( nome, cpf, dataNascimento);
		this.parentesco = parentesco;
		this.id_funcionario = id_funcionario;
		
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