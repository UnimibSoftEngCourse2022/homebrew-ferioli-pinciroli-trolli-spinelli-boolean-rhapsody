package it.unimib.brewday.model;


public class IngredienteDellaRicetta {
    private TipoIngrediente tipoIngrediente;
    private int dosaggioIngrediente;

    public TipoIngrediente getTipoIngrediente() {
        return tipoIngrediente;
    }

    public void setTipoIngrediente(TipoIngrediente tipoIngrediente) {
        this.tipoIngrediente = tipoIngrediente;
    }

    public int getDosaggioIngrediente() {
        return dosaggioIngrediente;
    }

    public void setDosaggioIngrediente(int dosaggioIngrediente) {
        this.dosaggioIngrediente = dosaggioIngrediente;
    }
}
