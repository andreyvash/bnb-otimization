import java.util.*;
import java.util.stream.Collector;
import java.util.stream.Collectors;

public class Elenco
{

    private static void debugEntrada(int nGrupos, int nAtores, int nPersonagens, List<Ator> atores)
    {
        System.out.print(nGrupos + " " + nAtores + " " + nPersonagens);
        System.out.println();

        for(Ator ator : atores)
        {
            ator.imprimeAtor();
        }
    }

    private static void adicionaGrupos(Ator ator, Set<Integer> gruposDoNo)
    {
        for(Integer grupo: ator.getGrupos())
        {
            gruposDoNo.add(grupo);
        }
    }

    private static void imprimeEstrutura(List<ProblemaElenco> problemas)
    {
        for(ProblemaElenco p : problemas)
        {

            for(Ator ator : p.getCandidatos())
            {
                ator.imprimeId();
            
            }

            System.out.print("(");
            for(Integer grupo : p.getGrupos())
            {
                System.out.print(grupo.intValue() + ", ");
            }    
            System.out.println(p.getCusto() + ")");
        }
    }

    private static void leEntrada(int numGrupos, int numAtores, int numPersonagens, Set<Integer> grupos, List<Ator> atores, Scanner sc)
    {
        for(int i = 0; i < numGrupos; i++)
        {
            grupos.add(Integer.valueOf(i+1));
        }

        for(int i = 0; i < numAtores; i++)
        {
            Ator ator = new Ator();
            ator.setId(i+1);
            ator.setCusto(sc.nextInt());
            ator.setNumGrupos(sc.nextInt());
            List<Integer> gruposAtor = new ArrayList<>();

            for(int j = 0; j < ator.getNumGrupos(); j++)
            {
                gruposAtor.add(Integer.valueOf(sc.nextInt()));
            }

            ator.setGrupos(gruposAtor);
            atores.add(ator);
        }
        sc.close();
    }

    private static Solucao resolveProblema(List<Ator> atores, List<ProblemaElenco> problema, Set<Integer> grupos, int numPersonagens)
    {
        List<Ator> contratados = new ArrayList<>();
        int custo = Integer.MAX_VALUE;

        for(ProblemaElenco p : problema)
        {
            if (p.getCusto() < custo && grupos.equals(p.getGrupos()))
            {
                custo = p.getCusto();
                contratados = p.getCandidatos();
            }
        }

        if(contratados.isEmpty())
        {
            System.err.println("Inviavel");
            return null;
        }
        else
        {
            int i = 0;
            while(contratados.size() < numPersonagens)
            {
                if (!(contratados.contains(atores.get(i))))
                {
                    contratados.add(atores.get(i));
                    custo += atores.get(i).getCusto();
                }
                i++;    
            }
            return new Solucao(custo, contratados);
        }
    }

    public static void criaEstrutura(List<Ator> atores, int numPersonagens, List<ProblemaElenco> problema)
    {
        for(int i = 0; i < atores.size(); i++)
        {
            Ator ator =  atores.get(i);
            List<Ator> atoresDoNo = new ArrayList<>();
            atoresDoNo.add(ator);

            ProblemaElenco instancia = new ProblemaElenco();
            Set<Integer> gruposNo = new HashSet<>();
            
            instancia.setCusto(ator.getCusto());
            adicionaGrupos(ator, gruposNo);
            
            for(int j = 0; j < atores.size(); j++)
            {
                if(i != j && !(gruposNo.containsAll(atores.get(j).getGrupos())))
                {
                    if(atoresDoNo.size() < numPersonagens )
                    {
                        ator = atores.get(j);
                        atoresDoNo.add(ator);
                        adicionaGrupos(ator, gruposNo);
                        instancia.setCusto(instancia.getCusto() + ator.getCusto());
                    }
                    else if(j < atores.size())
                    {
                        ator =  atores.get(i);
                        List<Ator> proximosFilhos = new ArrayList<>();
                        proximosFilhos.add(ator);
                        Set<Integer> gruposSegundoNo = new HashSet<>();

                        adicionaGrupos(ator, gruposSegundoNo);
                        ProblemaElenco proximosNo = new ProblemaElenco();
                        proximosNo.setCusto(ator.getCusto());
                        for(int k = j; k < atores.size(); k++)
                        {
                            if(i != k && !(gruposSegundoNo.containsAll(atores.get(k).getGrupos())))
                            {
                                if(proximosFilhos.size() < numPersonagens)
                                {
                                    ator = atores.get(k);
                                    proximosFilhos.add(ator);
                                    proximosNo.setCusto(proximosNo.getCusto() + ator.getCusto());
                                    adicionaGrupos(ator, gruposSegundoNo);
                                }
                            }
                        }
                        proximosNo.setCandidatos(proximosFilhos);
                        proximosNo.setGrupos(gruposSegundoNo);
                        problema.add(proximosNo);
                    }
                }
            }

            instancia.setGrupos(gruposNo);
            instancia.setCandidatos(atoresDoNo);
            problema.add(instancia);
        }
    }
    
    public static void main(String args[])
    {
        Scanner sc= new Scanner(System.in);    
        int numGrupos= sc.nextInt();  
        int numAtores= sc.nextInt();  
        int numPersonagens= sc.nextInt();
        Set<Integer> grupos = new HashSet<>();
        List<Ator> atores = new ArrayList<>();
        
        StringBuilder stf = new StringBuilder();
        for(int i = 0; i< args.length; i++)
        {
            stf.append(args[i]);
        }

        if(stf.toString().contains("-a"))
        {
            System.out.println("gay");
        }
        leEntrada(numGrupos, numAtores, numPersonagens, grupos, atores, sc);
        atores.sort(Comparator.comparing(Ator::getCusto));

        List<ProblemaElenco> problema = new ArrayList<>();
        criaEstrutura(atores, numPersonagens, problema);

        Solucao solucao = resolveProblema(atores, problema, grupos, numPersonagens);
        
        if(solucao != null)
            solucao.imprime();
        
    }
}