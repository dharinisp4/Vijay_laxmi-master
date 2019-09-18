package Model;

/**
 * Created by Rajesh Dabhi on 22/6/2017.
 */

public class Category_subcat_model {

    String id;
    String title;
    String slug;
    String parent;
    String leval;
    String description;
    String image;
    String status;
    String Count;
    String PCount;
    String product_description_arb;


    public String getId(){
        return id;
    }

    public String getTitle(){
        return title;
    }

    public String getSlug(){
        return slug;
    }

    public String getProduct_description_arb() {
        return product_description_arb;
    }


    public String getParent(){
        return parent;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setSlug(String slug) {
        this.slug = slug;
    }

    public void setParent(String parent) {
        this.parent = parent;
    }

    public void setLeval(String leval) {
        this.leval = leval;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setCount(String count) {
        Count = count;
    }

    public void setPCount(String PCount) {
        this.PCount = PCount;
    }

    public void setProduct_description_arb(String product_description_arb) {
        this.product_description_arb = product_description_arb;
    }


    public String getLeval(){
        return leval;
    }

    public String getDescription(){
        return description;
    }

    public String getImage(){
        return image;
    }

    public String getStatus(){
        return status;
    }




    public String getCount(){
        return Count;
    }

    public String getPCount(){
        return PCount;
    }

}
