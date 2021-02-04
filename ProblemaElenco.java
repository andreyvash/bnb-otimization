import java.util.*;

public class ProblemaElenco
{
    private int custo;
    private Set<Integer> grupos;
    private List<Ator> candidatos;

    public int getCusto() {
        return custo;
    }

    public void setCusto(int custo) {
        this.custo = custo;
    }

    public Set<Integer> getGrupos() {
        return grupos;
    }

    public void setGrupos(Set<Integer> grupos) {
        this.grupos = grupos;
    }

    public List<Ator> getCandidatos() {
        return candidatos;
    }

    public void setCandidatos(List<Ator> candidatos) {
        this.candidatos = candidatos;
    }

}