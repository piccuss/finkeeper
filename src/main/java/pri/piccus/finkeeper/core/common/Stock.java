package pri.piccus.finkeeper.core.common;

public record Stock(String name, String code, Double price, Double chg, Double chgr) {
    @Override
    public String toString() {
        return String.format("Stock{name='%s', code='%s', price=%.3f, chgr=%.2f%%, chg=%.3f", name, code, price, chg, chgr);
    }
}
