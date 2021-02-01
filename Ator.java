import java.util.*;

public class Ator
{
    private int id;
    private int custo;
    private List<Integer> grupos;
    private int numGrupos;

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

    public List<Integer> getGrupos() {
        return grupos;
    }

    public void setGrupos(List<Integer> grupos) {
        this.grupos = grupos;
    }

    public int getNumGrupos() {
        return numGrupos;
    }

    public void setNumGrupos(int numGrupos) {
        this.numGrupos = numGrupos;
    }

    public void imprimeAtor()
    {
        System.out.print(this.custo + " " + this.numGrupos);
        System.out.println();

        for(int i = 0; i < this.numGrupos; i++)
        {
            System.out.println(this.grupos.get(i));
        }
    }

    public void imprimeId()
    {
        System.out.print(this.id + " -> ");

    }
}