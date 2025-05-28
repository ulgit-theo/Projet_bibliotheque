
public class AmendeFixe implements FineStrategy {
    private double montantParJour;
    public AmendeFixe(double montantParJour) {
        this.montantParJour = montantParJour;
    }
    @Override
    public double calculerAmende(int joursRetard) {
        return joursRetard * montantParJour;
    }
}
