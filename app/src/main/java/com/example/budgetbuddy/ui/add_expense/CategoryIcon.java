package com.example.budgetbuddy.ui.add_expense;

public class CategoryIcon {
    private int categoryId;
    private String categoryName;
    private int categoryPic;

    public CategoryIcon(int categoryId, String categoryName, int categoryPic) {
        this.categoryId = categoryId;
        this.categoryName = categoryName;
        this.categoryPic = categoryPic;
    }

    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public int getCategoryPic() {
        return categoryPic;
    }

    public void setCategoryPic(int categoryPic) {
        this.categoryPic = categoryPic;
    }
}
