public class Main {

    public static void main(String[] args) {

        Vedge.start();

        JSONObject obj = JSONObject.of("{this:that,that:1,that_again:{this:also_that,1:2}}");

        System.out.println(obj.getValue("that_again.1.3"));

        obj.setValue("that_again.this",new JSONValue("not_that"));

        System.out.println(obj);

    }

}
