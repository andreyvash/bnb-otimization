import java.util.*;

public class Node
{
    private Set<Integer> grupos;
    private Ator ator;
    private int custo;
    private List<Node> filhos;
    
    public Set<Integer> getGrupos() {
        return grupos;
    }

    public void setGrupos(Set<Integer> grupos) {
        this.grupos = grupos;
    }

    public Ator getAtor() {
        return ator;
    }

    public void setAtor(Ator ator) {
        this.ator = ator;
    }

    public int getCusto() {
        return custo;
    }

    public void setCusto(int custo) {
        this.custo = custo;
    }

    public Node(Set<Integer> grupos, Ator ator, int custo, List<Node> filhos) {
        this.grupos = grupos;
        this.ator = ator;
        this.custo = custo;
        this.filhos = filhos;
    }

    public List<Node> getFilhos() {
        return filhos;
    }

    public void setFilhos(List<Node> filhos) {
        this.filhos = filhos;
    }

    
}