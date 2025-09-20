package calcularsalario;

public enum Aliquotas {
		//IR
		ALIQUOTAIR1(0.075), ALIQUOTAIR2(0.15),ALIQUOTAIR3(0.225),ALIQUOTAIR4(0.275), 
		//INSS
		ALIQUOTAINSS1(0.075), ALIQUOTAINSS2(0.09), ALIQUOTAINSS3(0.12), ALIQUOTAINSS4(0.14),
		//DEDUCAO INSS
		DEDUCAOINSS1(22.77),DEDUCAOINSS2(106.59),DEDUCAOINSS3(190.40),
		//DEDUCAO IR
		DEDUCAOIR1(169.44),DEDUCAOIR2(381.44),DEDUCAOIR3(662.77),DEDUCAOIR4(896.);
		
	
	private final double aliquotas;

		private Aliquotas(double aliquotas) {
			this.aliquotas = aliquotas;
		}

		public double getAliquotas() {
			return aliquotas;
		}
		
		
}