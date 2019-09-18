package Model;

/**
 * Created by Rajesh Dabhi on 22/6/2017.
 */

public class Slider_subcat_model {

    String id;
    String title;
    String slug;
    String parent;
    String leval;
    String description;
    String image;
    String arb_title;
    String status;


    public String getId(){
        return id;
    }

    public String getTitle(){
        return title;
    }

    public String getSlug(){
        return slug;
    }

    public String getParent(){
        return parent;
    }

    public String getLeval(){
        return leval;
    }

    public String getArb_title() {
        return arb_title;
    }

    public void setArb_title(String arb_title) {
        this.arb_title = arb_title;
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


}
