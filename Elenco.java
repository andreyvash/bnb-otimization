import java.util.*;
import java.util.stream.Collector;
import java.util.stream.Collectors;

public class Elenco
{

    public static void debugEntrada(int nGrupos, int nAtores, int nPersonagens, List<Ator> atores)
    {
        System.out.print(nGrupos + " " + nAtores + " " + nPersonagens);
        System.out.println();

        for(Ator ator : atores)
        {
            ator.imprimeAtor();
        }
    }

    public static void adicionaGrupos(Ator ator, Set<Integer> gruposDoNo)
    {
        for(Integer grupo: ator.getGrupos())
        {
            gruposDoNo.add(grupo);
        }
    }

    public static void criaEstrutura(List<Ator> atores, int numPersonagens, List<ProblemaElenco> problema)
    {
        for(int i = 0; i < atores.size(); i++)
        {
            Ator ator =  atores.get(i);
            List<Ator> atoresDoNo = new ArrayList<>();
            atoresDoNo.add(ator);
            int numElencados = 1;
            ProblemaElenco instancia = new ProblemaElenco();
            Set<Integer> gruposNo = new HashSet<>();
            instancia.setCusto(ator.getCusto());
            adicionaGrupos(ator, gruposNo);
            instancia.setNumPersonagens(numElencados);
            
            for(int j = 0; j < atores.size(); j++)
            {
                if(i != j && !(gruposNo.containsAll(atores.get(j).getGrupos())))
                {
                    if(instancia.getNumPersonagens() < numPersonagens )
                    {
                        ator = atores.get(j);
                        atoresDoNo.add(ator);
                        adicionaGrupos(ator, gruposNo);
                        instancia.setCusto(instancia.getCusto() + ator.getCusto());
                        instancia.setNumPersonagens(instancia.getNumPersonagens()+1);
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


        // debugEntrada(numGrupos, numAtores, numPersonagens, atores);
        atores.sort(Comparator.comparing(Ator::getCusto));
        debugEntrada(numGrupos, numAtores, numPersonagens, atores);

        List<ProblemaElenco> problema = new ArrayList<>();
        criaEstrutura(atores, numPersonagens, problema);

        List<Ator> contratados = new ArrayList<>();
        int custo = Integer.MAX_VALUE;

        for(ProblemaElenco p : problema)
        {

            if (p.getCusto() < custo && grupos.equals(p.getGrupos()))
            {
                custo = p.getCusto();
                contratados = p.getCandidatos();
            }

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

        if(contratados.isEmpty())
        {
            System.out.println("Inviavel");
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
            contratados.sort(Comparator.comparing(Ator::getId));
            for(Ator ator : contratados)
            {
                System.out.print(ator.getId() + " ");
            }
            System.out.println();
            System.out.println(custo);
        }
        
    }
}