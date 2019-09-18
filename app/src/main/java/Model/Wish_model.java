package Model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class Wish_model {

    String product_id;
    String product_name;
    String cat_id;
    String product_description;
    String product_attribute;
     String price;
    String product_images;
     String stock;
    String unit_value;
    String unit;
    String increament;
    String rewards;
    String title;
     String mrp;





    @SerializedName("sub_cat")
    ArrayList<Category_subcat_model> category_sub_datas;

    public String getProduct_id() {
        return product_id;
    }

    public void setProduct_id(String product_id) {
        this.product_id = product_id;
    }

    public String getProduct_name() {
        return product_name;
    }

    public void setProduct_name(String product_name) {
        this.product_name = product_name;
    }

    public String getCat_id() {
        return cat_id;
    }

    public void setCat_id(String cat_id) {
        this.cat_id = cat_id;
    }

    public String getProduct_description() {
        return product_description;
    }

    public void setProduct_description(String product_description) {
        this.product_description = product_description;
    }

    public String getProduct_attribute() {
        return product_attribute;
    }

    public void setProduct_attribute(String product_attribute) {
        this.product_attribute = product_attribute;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getProduct_images() {
        return product_images;
    }

    public void setProduct_images(String product_images) {
        this.product_images = product_images;
    }

    public String getStock() {
        return stock;
    }

    public void setStock(String stock) {
        this.stock = stock;
    }

    public String getUnit_value() {
        return unit_value;
    }

    public void setUnit_value(String unit_value) {
        this.unit_value = unit_value;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public String getIncreament() {
        return increament;
    }

    public void setIncreament(String increament) {
        this.increament = increament;
    }

    public String getRewards() {
        return rewards;
    }

    public void setRewards(String rewards) {
        this.rewards = rewards;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getMrp() {
        return mrp;
    }

    public void setMrp(String mrp) {
        this.mrp = mrp;
    }

    public ArrayList<Category_subcat_model> getCategory_sub_datas() {
        return category_sub_datas;
    }

    public void setCategory_sub_datas(ArrayList<Category_subcat_model> category_sub_datas) {
        this.category_sub_datas = category_sub_datas;
    }
}
