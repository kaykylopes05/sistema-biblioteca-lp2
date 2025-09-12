public class DVD extends ItemDoAcervo {

    private int DuracaoMinutos;

    public DVD(String titulo, int ano, int DuracaoMinutos) {

        super(titulo, ano);
        this.DuracaoMinutos = DuracaoMinutos;
    }

    @Override
    public int getPrazo(){
        return 3;
    }

    @Override
    public double getValorMultaPorDiaAtraso(){
        return 2;
    }

    @Override
    public String toString() {
        return "DVD '" + getTitulo() + "' de (" + getAno() + ") - " + DuracaoMinutos + " - Status: " + getStatus();

    }
}
