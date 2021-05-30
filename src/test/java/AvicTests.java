import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.List;
import java.util.Optional;

public class AvicTests extends BaseTest {

    @Test
    public void testGoToNotebookSectionFromSidebar() {
        getHomePage().clickSideBar()
                .goToInnerCategory("noutbuki-i-aksessuaryi")
                .goToProducts("noutbuki")
                .clickProductWithFilter("xiaomi");

        getProductPage().getProdElementsDescText()
                .forEach(element -> Assert.assertTrue(element.contains("Xiaomi")));
    }

    @Test
    public void testFilterBlock() {

        List<String> filterValues = List.of("dlya-biznesa", "tonkij-i-lyogkij");

        getHomePage().clickSideBar()
                .goToInnerCategory("noutbuki-i-aksessuaryi")
                .clickInnerCategoryItem("noutbuki");

        getProductPage().clickFilterBlocks(filterValues);

        Assert.assertTrue(getProductPage().getDriver().getCurrentUrl()
                .contains("tip-noutbuka--dlya-biznesa--tonkij-i-lyogkij"));

    }

    @Test
    public void testFilterBlockHiddenElementsAndVerifyElementProperty() {
        List<String> filterValues = List.of("est");
        String definedChar = "сенсорный";

        getHomePage().clickSideBar()
                .goToInnerCategory("noutbuki-i-aksessuaryi")
                .clickInnerCategoryItem("noutbuki");

        getProductPage().openClosedFilterBox("Сенсорный экран")
                .clickFilterBlocks(filterValues)
                .clickProductIconByNumber(2);

        Optional<String> result =
                getItemPage().getCharacteristicPresent(definedChar);

        Assert.assertTrue(result.isPresent());
    }

    @Test
    public void testAddAndRemoveFromCart() {
        String expected = "0";

        getHomePage().clickPopularItem(0);

        getItemPage().clickBuyButton()
                .clickCart()
                .clickRemoveItemFromCartButton(0);

        String resultCount = getHomePage().getCartItemCountText();

        Assert.assertEquals(resultCount, expected);
    }
}
