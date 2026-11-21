package co.edu.udea.qa.advantageshopping.utils;

import co.edu.udea.qa.advantageshopping.models.CartLine;
import net.serenitybdd.screenplay.Actor;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static co.edu.udea.qa.advantageshopping.utils.Constants.CART_LINES_MEMORY_KEY;

/**
 * Guarda en la memoria del actor los productos que puso en el carrito y el precio unitario que vio
 * en la tienda, para comparar los valores esperados contra lo que calcula la tienda.
 */
public final class CartNotebook {

    private CartNotebook() {
    }

    public static List<CartLine> linesOf(Actor actor) {
        List<CartLine> lines = actor.recall(CART_LINES_MEMORY_KEY);
        return lines == null ? new ArrayList<>() : new ArrayList<>(lines);
    }

    public static void write(Actor actor, CartLine newLine) {
        List<CartLine> lines = linesOf(actor);
        lines.removeIf(line -> line.getProductName().equalsIgnoreCase(newLine.getProductName()));
        lines.add(newLine);
        actor.remember(CART_LINES_MEMORY_KEY, lines);
    }

    public static void erase(Actor actor, String productName) {
        List<CartLine> lines = linesOf(actor);
        lines.removeIf(line -> line.getProductName().equalsIgnoreCase(productName));
        actor.remember(CART_LINES_MEMORY_KEY, lines);
    }

    public static void changeQuantity(Actor actor, String productName, int quantity) {
        linesOf(actor).stream()
                .filter(line -> line.getProductName().equalsIgnoreCase(productName))
                .findFirst()
                .ifPresent(line -> write(actor, new CartLine(line.getProductName(), line.getUnitPrice(), quantity)));
    }

    public static BigDecimal expectedTotal(Actor actor) {
        return linesOf(actor).stream()
                .map(CartLine::subtotal)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    public static int expectedItems(Actor actor) {
        return linesOf(actor).stream().mapToInt(CartLine::getQuantity).sum();
    }
}
