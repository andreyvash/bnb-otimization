import java.util.*;

public class Solucao
{
    private int custo;
    private List<Ator> contratados;

    public Solucao(int custo, List<Ator> candidatos) 
    {
        this.custo = custo;
        this.contratados = candidatos;
    }

    public int getCusto() {
        return custo;
    }

    public void setCusto(int custo) {
        this.custo = custo;
    }

    public List<Ator> getContratados() {
        return contratados;
    }

    public void setContratados(List<Ator> contratados) {
        this.contratados = contratados;
    }

    public void imprime()
    {
        this.contratados.sort(Comparator.comparing(Ator::getId));
        for(Ator ator : contratados)
        {
            System.out.print(ator.getId() + " ");
        }
        System.out.println();
        System.out.println(custo);
    }
    
}