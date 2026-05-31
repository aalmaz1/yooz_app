package javax.mail.search;

/* JADX INFO: loaded from: classes3.dex */
public abstract class IntegerComparisonTerm extends ComparisonTerm {
    private static final long serialVersionUID = -6963571240154302484L;
    protected int number;

    protected IntegerComparisonTerm(int i, int i2) {
        this.comparison = i;
        this.number = i2;
    }

    public int getNumber() {
        return this.number;
    }

    public int getComparison() {
        return this.comparison;
    }

    protected boolean match(int i) {
        switch (this.comparison) {
            case 1:
                if (i > this.number) {
                    break;
                }
                break;
            case 2:
                if (i >= this.number) {
                    break;
                }
                break;
            case 3:
                if (i != this.number) {
                    break;
                }
                break;
            case 4:
                if (i == this.number) {
                    break;
                }
                break;
            case 5:
                if (i <= this.number) {
                    break;
                }
                break;
            case 6:
                if (i < this.number) {
                    break;
                }
                break;
        }
        return false;
    }

    @Override // javax.mail.search.ComparisonTerm
    public boolean equals(Object obj) {
        return (obj instanceof IntegerComparisonTerm) && ((IntegerComparisonTerm) obj).number == this.number && super.equals(obj);
    }

    @Override // javax.mail.search.ComparisonTerm
    public int hashCode() {
        return this.number + super.hashCode();
    }
}
