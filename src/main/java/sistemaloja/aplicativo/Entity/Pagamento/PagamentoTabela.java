package sistemaloja.aplicativo.Entity.Pagamento;

import javafx.beans.property.*;

public class PagamentoTabela {
    private final IntegerProperty id = new SimpleIntegerProperty();
    private final StringProperty cliente = new SimpleStringProperty();
    private final StringProperty data = new SimpleStringProperty();
    private final DoubleProperty preco = new SimpleDoubleProperty();
    private final StringProperty status = new SimpleStringProperty();

    public PagamentoTabela(int id, String cliente, String data, double preco, String status) {
        this.id.set(id);
        this.cliente.set(cliente);
        this.data.set(data);
        this.preco.set(preco);
        this.status.set(status);
    }

    public IntegerProperty idProperty() { return id; }
    public StringProperty clienteProperty() { return cliente; }
    public StringProperty dataProperty() { return data; }
    public DoubleProperty precoProperty() { return preco; }
    public StringProperty statusProperty() { return status; }
}
