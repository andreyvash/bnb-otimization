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

    private static Long contaNos(List<ProblemaElenco> problemas)
    {
        Long cont = 0L;
        for(ProblemaElenco p : problemas)
        {
            cont++;
            cont += p.getCandidatos().size();
        }
        return cont;
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

    private static boolean isFuncaoPadrao(String funcaoLimit)
    {
        if("Padrao".equals(funcaoLimit))
            return true;
        return false;    
    }

    public static void criaEstrutura(List<Ator> atores, int numPersonagens, List<ProblemaElenco> problema, Set<Integer> grupos, String funcaoLimit)
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
                // if(i != j && !(gruposNo.containsAll(atores.get(j).getGrupos())))
                if(i != j && (instancia.getCusto() >= atores.get(j).getCusto() && !isFuncaoPadrao(funcaoLimit) ||
                             (!(gruposNo.containsAll(atores.get(j).getGrupos()))) && isFuncaoPadrao(funcaoLimit)))
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
                            // if(i != k && !(gruposSegundoNo.containsAll(atores.get(k).getGrupos())))
                            if(i != k && (proximosNo.getCusto() >= atores.get(k).getCusto()))
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
                else if (gruposNo.containsAll(grupos))
                {
                    instancia.setGrupos(gruposNo);
                    instancia.setCandidatos(atoresDoNo);
                    problema.add(instancia);
                    return;
                }
                    
            }

            instancia.setGrupos(gruposNo);
            instancia.setCandidatos(atoresDoNo);
            problema.add(instancia);
        }
    }
    
    private static void criaArvore(List<Ator> atores, Set<Integer> grupos, int numPersonagens)
    {
        LinkedList<Node> stack = new LinkedList<>();

        Node root = new Node(new HashSet<>(), new Ator(), Integer.MAX_VALUE, new ArrayList<>());

        stack.add(root);
        while(!stack.isEmpty())
        {
            Node node = stack.pollLast();
            for(Ator ator : atores)
            {
                List<Node> filhos = new ArrayList<>();
                Set<Integer> gruposNo = new HashSet<>();
                gruposNo.addAll(ator.getGrupos());    
                Node filho = new Node(gruposNo, ator, ator.getCusto(), new ArrayList<>());
                filhos.add(filho);
                List<Ator> atoresRestantes = atores.subList(atores.indexOf(ator), atores.indexOf(ator));
                
                for(Ator atorFilho : atoresRestantes)
                {
                    if(!(node.getGrupos().containsAll(atorFilho.getGrupos())))
                    {   
                        gruposNo.addAll(atorFilho.getGrupos());
                        node.setGrupos(gruposNo);
                        filho = new Node(gruposNo, atorFilho, filho.getCusto()+atorFilho.getCusto(), filhos);
                        filhos.add(filho);
                    }
                    node.setFilhos(filhos);
                }

            }
        }

    }
    public static void main(String args[]) throws Exception
    {
        Scanner sc= new Scanner(System.in);    
        int numGrupos= sc.nextInt();  
        int numAtores= sc.nextInt();  
        int numPersonagens= sc.nextInt();
        Set<Integer> grupos = new HashSet<>();
        List<Ator> atores = new ArrayList<>();
        Long nosPercorridos;
        StringBuilder funcaoLimitante = new StringBuilder("Padrao");

        StringBuilder stf = new StringBuilder();
        for(int i = 0; i< args.length; i++)
        {
            stf.append(args[i]);
        }

        if(stf.toString().contains("-a"))
        {
            funcaoLimitante = new StringBuilder("Alternativa");;
        }

        leEntrada(numGrupos, numAtores, numPersonagens, grupos, atores, sc);

        long start = System.currentTimeMillis();

        atores.sort(Comparator.comparing(Ator::getCusto));
        List<ProblemaElenco> problema = new ArrayList<>();
        criaEstrutura(atores, numPersonagens, problema, grupos, funcaoLimitante.toString());
        nosPercorridos = contaNos(problema);
        imprimeEstrutura(problema);
        Solucao solucao = resolveProblema(atores, problema, grupos, numPersonagens);
        
        if(solucao != null)
        {
            solucao.imprime();
        }
            
        long time = System.currentTimeMillis() - start;
        System.err.println("Tempo: " + time + "ms");
        System.err.println("Nós da arvore: " + nosPercorridos);

    }
}