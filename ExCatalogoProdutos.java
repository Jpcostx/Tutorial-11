import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

public class ExCatalogoProdutos {

    static class Produto implements Comparable<Produto> {
        private String codigo;
        private String descricao;
        private double preco;
        private int quantidade;

        public Produto(String codigo, String descricao, double preco, int quantidade) {
            if (preco < 0 || quantidade < 0) {
                throw new IllegalArgumentException("O preco e a quantidade nao podem ser negativos.");
            }
            this.codigo = codigo;
            this.descricao = descricao;
            this.preco = preco;
            this.quantidade = quantidade;
        }

        public String getCodigo() {
            return codigo;
        }

        public String getDescricao() {
            return descricao;
        }

        public double getPreco() {
            return preco;
        }

        public int getQuantidade() {
            return quantidade;
        }

        @Override
        public boolean equals(Object obj) {
            if (this == obj) return true;
            if (obj == null || getClass() != obj.getClass()) return false;
            Produto produto = (Produto) obj;
            return Objects.equals(codigo, produto.codigo);
        }

        @Override
        public int hashCode() {
            return Objects.hash(codigo);
        }

        @Override
        public int compareTo(Produto outro) {
            return Double.compare(this.preco, outro.preco);
        }

        @Override
        public String toString() {
            return String.format("[%s] %s | Qtd: %d | Preco: R$ %.2f", codigo, descricao, quantidade, preco);
        }
    }

    static class CatalogoService {
        private final List<Produto> produtos = new ArrayList<>();

        public void cadastrar(Produto p) {
            if (p == null) {
                throw new IllegalArgumentException("Nao e permitido cadastrar produtos nulos.");
            }
            if (produtos.contains(p)) {
                throw new IllegalStateException("Ja existe um produto cadastrado com o codigo: " + p.getCodigo());
            }
            produtos.add(p);
        }

        public Produto obterPorPosicao(int indice) {
            if (indice < 0 || indice >= produtos.size()) {
                throw new IndexOutOfBoundsException("Posicao invalida. O indice " + indice + " esta fora dos limites do catalogo.");
            }
            return produtos.get(indice);
        }

        public boolean excluirPorCodigo(String codigo) {
            return produtos.removeIf(p -> p.getCodigo().equals(codigo));
        }

        public void ordenarPorPreco() {
            Collections.sort(produtos);
        }

        public List<Produto> listarTodos() {
            return new ArrayList<>(produtos);
        }
    }

    public static void main(String[] args) {
        CatalogoService servico = new CatalogoService();

        System.out.println("=== 1. CADASTRANDO PRODUTOS FORA DE ORDEM ===");
        servico.cadastrar(new Produto("PRD-001", "Cadeira Gamer", 1250.00, 5));
        servico.cadastrar(new Produto("PRD-002", "Mousepad Gigante", 85.50, 20));
        servico.cadastrar(new Produto("PRD-003", "Monitor 27 Polegadas", 1800.00, 3));
        servico.cadastrar(new Produto("PRD-004", "Teclado Mecanico", 350.00, 10));

        listarComIndices(servico);

        System.out.println("\n=== 2. EXCLUINDO PRODUTO INTERMEDIARIO (PRD-003) ===");
        boolean removido = servico.excluirPorCodigo("PRD-003");
        System.out.println("Produto removido com sucesso? " + removido);
        listarComIndices(servico);

        System.out.println("\n=== 3. ORDENANDO POR PRECO (CRESCENTE) ===");
        servico.ordenarPorPreco();
        listarComIndices(servico);
    }

    private static void listarComIndices(CatalogoService servico) {
        System.out.println("--- Listagem Atual do Catalogo ---");
        List<Produto> listaAtual = servico.listarTodos();
        for (int i = 0; i < listaAtual.size(); i++) {
            System.out.println("Indice " + i + ": " + listaAtual.get(i));
        }
    }
}