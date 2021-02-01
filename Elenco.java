import java.util.*;

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

    public static void criaEstrutura(List<Ator> atores, int numPersonagens, List<ProblemaElenco> problema)
    {
        for(int i = 0; i < atores.size(); i++)
        {
            Ator ator =  atores.get(i);
            List<Ator> atoresDoNo = new ArrayList<>();
            atoresDoNo.add(ator);
            int numElencados = 1;
            ProblemaElenco instancia = new ProblemaElenco();

            instancia.setNumPersonagens(numElencados);

            for(int j = 0; j < atores.size(); j++)
            {
                if(i != j && atores.get(j).getCusto() >= atoresDoNo.get(0).getCusto())
                {
                    if(instancia.getNumPersonagens() < numPersonagens)
                    {
                        ator = atores.get(j);
                        atoresDoNo.add(ator);
                        instancia.setNumPersonagens(instancia.getNumPersonagens()+1);
                    }
                    else if(j < atores.size())
                    {
                        ator =  atores.get(i);
                        List<Ator> proximosFilhos = new ArrayList<>();
                        proximosFilhos.add(ator);
                        
                        ProblemaElenco proximosNo = new ProblemaElenco();
                        for(int k = j; k < atores.size(); k++)
                        {
                            if(i != k && atores.get(k).getCusto() >= proximosFilhos.get(0).getCusto())
                            {
                                if(proximosFilhos.size() < numPersonagens)
                                {
                                    ator = atores.get(k);
                                    proximosFilhos.add(ator);
                                }
                            }
                        }
                        proximosNo.setCandidatos(proximosFilhos);
                        problema.add(proximosNo);
                    }
                }
            }

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
        List<Integer> grupos = new ArrayList<>();
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
            grupos.add(Integer.valueOf(i));
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
        // debugEntrada(numGrupos, numAtores, numPersonagens, atores);

        List<ProblemaElenco> problema = new ArrayList<>();
        criaEstrutura(atores, numPersonagens, problema);

        for(ProblemaElenco p : problema)
        {
            for(Ator ator : p.getCandidatos())
                ator.imprimeId();
            System.out.println();
        }
    }
}