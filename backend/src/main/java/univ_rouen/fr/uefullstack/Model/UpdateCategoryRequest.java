package univ_rouen.fr.uefullstack.Model;



import java.util.List;


public class UpdateCategoryRequest {

    private Category category;

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public List<Long> getSelectedChildrenIds() {
        return selectedChildrenIds;
    }

    public void setSelectedChildrenIds(List<Long> selectedChildrenIds) {
        this.selectedChildrenIds = selectedChildrenIds;
    }

    private List<Long> selectedChildrenIds;

}
