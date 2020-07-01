package application;

public class MasennustestiKysymys {
	int kysymysNro;
	String kysymysTeksti;
	public String[] vaihtoehdot = new String[4];
	int annettuVastaus;
	int[] pistemaara = new int [4];
	boolean vastattu;
	
	public MasennustestiKysymys(int kysymysNro, String kysymysTeksti, String vaihtoehto1, String vaihtoehto2, String vaihtoehto3, String vaihtoehto4)
	{
		this.kysymysNro = kysymysNro;
		this.kysymysTeksti = kysymysTeksti;
		this.vaihtoehdot[0] = " 0. " + vaihtoehto1; this.pistemaara [0] = 0;
		this.vaihtoehdot[1] = " 1. " + vaihtoehto2; this.pistemaara [1] = 1;
		this.vaihtoehdot[2] = " 2. " + vaihtoehto3; this.pistemaara [2] = 2;
		this.vaihtoehdot[3] = " 3. " + vaihtoehto4; this.pistemaara [3] = 3;
		this.annettuVastaus = 0;
		this.vastattu = false;
	}
	public void asetaVastaus(int annettuVastaus) {
	
		this.annettuVastaus = annettuVastaus;
		
		/*System.out.println("kysymysNro: " + this.kysymysNro);
		System.out.println("kysymysTeksti: " + this.kysymysTeksti);
		System.out.println("kysymysVastaus: " + this.annettuVastaus); */
		this.vastattu = true;
	}

	public int annaVastaus() {
		return this.annettuVastaus;
	}
}