package entity;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

import calcularsalario.Aliquotas;
import calcularsalario.DependenteException;
import calcularsalario.RegraDeNegocio;

public class Funcionario extends Pessoa implements RegraDeNegocio {
	private double salarioBruto;
	private double descontoInss;
	private double descontoIr;

	private Set<Dependente> dependentes = new HashSet<>();

	public Funcionario(String nome, String cpf, LocalDate dataNascimento, double salarioBruto) {
		super(nome, cpf, dataNascimento);
		this.salarioBruto = salarioBruto;
		this.dependentes = new HashSet<>();
	}

	@Override
	// devolvendo já formatado, ver com o william se vai ser relevante
	public String toString() {
		return super.toString() + ("\nSalario Bruto: R$ " + String.format("%.2f", salarioBruto)
				+ "\nSalario Liquido: R$ " + String.format("%.2f", calcularSalarioLiquido()) + "\nDesconto INSS: R$ "
				+ String.format("%.2f", calcularINSS()) + "\nDesconto IR: R$ " + String.format("%.2f", calcularIR()));
	}

	public double getSalarioBruto() {
		return salarioBruto;
	}

	public void setSalarioBruto(double salarioBruto) {
		this.salarioBruto = salarioBruto;
	}

	public double getDescontoInss() {
		return descontoInss;
	}

	public void setDescontoInss(double descontoInss) {
		this.descontoInss = descontoInss;
	}

	public double getDescontoIr() {
		return descontoIr;
	}

	public void setDescontoIr(double descontoIr) {
		this.descontoIr = descontoIr;
	}

	@Override
	public double calcularINSS() {
		double salario = salarioBruto;
		double aliquota = 0;
		double deducao = 0;

		if (salario <= 1518.0) {
			aliquota = Aliquotas.ALIQUOTAINSS1.getAliquotas();
			deducao = Aliquotas.DEDUCAOINSS1.getAliquotas();
		} else if (salario <= 2793.88) {
			aliquota = Aliquotas.ALIQUOTAINSS2.getAliquotas();
			deducao = Aliquotas.DEDUCAOINSS2.getAliquotas();
		} else if (salario <= 4190.83) {
			aliquota = Aliquotas.ALIQUOTAINSS3.getAliquotas();
			deducao = Aliquotas.DEDUCAOINSS3.getAliquotas();
		} else if (salario <= 8157.41) {
			aliquota = Aliquotas.ALIQUOTAINSS4.getAliquotas();
			deducao = Aliquotas.DEDUCAOINSS3.getAliquotas();
		} else {
			aliquota = Aliquotas.ALIQUOTAINSS4.getAliquotas();
		}

		descontoInss = (salario * aliquota) - deducao;
		return descontoInss;

	}

	@Override
	public double calcularIR() {
		double dep = dependentes.size() * 189.59;
		double salario = salarioBruto - dep - descontoInss;
		double aliquota = 0;
		double deducao = 0;

		if (salario <= 2259.0) {
			return 0;
		}

		if (salario <= 2826.65) {
			aliquota = Aliquotas.ALIQUOTAIR1.getAliquotas();
			deducao = Aliquotas.DEDUCAOIR1.getAliquotas();
		} else if (salario <= 3751.05) {
			aliquota = Aliquotas.ALIQUOTAIR2.getAliquotas();
			deducao = Aliquotas.DEDUCAOIR2.getAliquotas();
		} else if (salario <= 4664.68) {
			aliquota = Aliquotas.ALIQUOTAIR3.getAliquotas();
			deducao = Aliquotas.DEDUCAOIR3.getAliquotas();
		} else if (salario > 4664.68) {
			aliquota = Aliquotas.ALIQUOTAIR4.getAliquotas();
			deducao = Aliquotas.DEDUCAOIR4.getAliquotas();
		}

		descontoIr = (salario * aliquota) - deducao;
		return descontoIr;
	}

	@Override
	public double calcularSalarioLiquido() {
		this.descontoInss = calcularINSS();
		this.descontoIr = calcularIR();

		return salarioBruto - descontoInss - descontoIr;
	}

	public void adicionarDependente(Dependente dependente) throws DependenteException {
		try {
			if (this.dependentes.add(dependente)) {
				System.out.println(dependente.getNome() + " adicionado com sucesso.");
			}
		} catch (Exception e) {
			System.out.println("Não é permitido cpf repetido, " + dependente.getNome() + "não foi incluído.");
		}
	}
}