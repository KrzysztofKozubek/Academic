import java.util.function.IntPredicate;

public class PMO_IntPredicateFactory {
	public static IntPredicate exactlyOne() {
		return e -> e == 1;
	}

	public static IntPredicate exactlyZero() {
		return e -> e == 0;
	}
	
	public static IntPredicate moreThenOne() {
		return e -> e > 1;
	}
	
	public static IntPredicate not( IntPredicate ie ) {
		return e -> ( ! ie.test( e ) );
	}
	
}
