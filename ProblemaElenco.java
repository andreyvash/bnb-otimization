import java.util.*;

public class ProblemaElenco
{
    private int id;
    private int custo;
    private Set<Integer> grupos;
    private int numGrupos;
    private int numPersonagens;
    private List<Ator> candidatos;

    public ProblemaElenco(int id, int custo, Set<Integer> grupos, int numGrupos, int numPersonagens,
    List<Ator> candidatos) 
    {
        this.id = id;
        this.custo = custo;
        this.grupos = grupos;
        this.numGrupos = numGrupos;
        this.numPersonagens = numPersonagens;
        this.candidatos = candidatos;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

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

    public int getNumGrupos() {
        return numGrupos;
    }

    public void setNumGrupos(int numGrupos) {
        this.numGrupos = numGrupos;
    }


    public List<Ator> getCandidatos() {
        return candidatos;
    }

    public void setCandidatos(List<Ator> candidatos) {
        this.candidatos = candidatos;
    }

    public int getNumPersonagens() {
        return numPersonagens;
    }

    public void setNumPersonagens(int numPersonagens) {
        this.numPersonagens = numPersonagens;
    }

	public ProblemaElenco() {
	}
}