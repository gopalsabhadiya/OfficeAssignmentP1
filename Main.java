import java.util.*;
import java.util.stream.Collectors;

public class Main {

    //This map will contain "Item as key" and "List of orders containing that item as value".
    //When there's new placed orders, this hashmap will be updated just for those new orders
    static Map<Item, List<Integer>> itemOrderHashMap = new HashMap<>();

    //Top two items that are available in list of orders
    static Item item1No;
    static Item item2No;
    static Integer max1 = Integer.MIN_VALUE;
    static Integer max2 = Integer.MIN_VALUE;

    public static void main(String[] args) {

        Item i1 = new Item(1);
        Item i2 = new Item(2);
        Item i3 = new Item(3);
        Item i4 = new Item(4);
        Item i5 = new Item(5);
        Item i6 = new Item(6);

        Order o1 = new Order(1, new ArrayList<Item>(Arrays.asList(i1, i2, i5, i4)));
        Order o2 = new Order(2, new ArrayList<Item>(Arrays.asList(i6, i5)));
        Order o3 = new Order(3, new ArrayList<Item>(Arrays.asList(i2, i5, i4)));
        Order o4 = new Order(4, new ArrayList<Item>(Arrays.asList(i3, i2, i6, i1, i5)));

        System.out.println("Output after placing order no: 1,2,3,4");
        process(new ArrayList<Order>(Arrays.asList(o1,o2,o3,o4)));

        System.out.println("\nNow placing two new orders no: 5,6");
        Order o5 = new Order(5, new ArrayList<Item>(Arrays.asList(i2,i3)));
        Order o6 = new Order(6, new ArrayList<Item>(Arrays.asList(i2,i6)));

        process(new ArrayList<Order>(Arrays.asList(o5, o6)));
    }

    /*
    This method will
      1. Take newly placed list of orders and update the hashtable
      2. Process the hashtable to find out most ordered top two items
     */
    private static void process(ArrayList<Order> orders) {
		
		//For each  item in individual order, add order to the respoctive item field in hashmap
        orders.forEach(order -> {
            List<Item> li = order.getItems();
            li.forEach(item -> {
                itemOrderHashMap.putIfAbsent(item, new ArrayList<>());
                itemOrderHashMap.get(item).add(order.getOrderNo());
            });
        });
		
		//Finding top two items present in maximum orders
        itemOrderHashMap.forEach((item, list) -> {
            if(itemOrderHashMap.get(item).size() > max1) {
                max1 = itemOrderHashMap.get(item).size();
                item1No = item;
            }
            else if(itemOrderHashMap.get(item).size() > max2) {
                max2 = itemOrderHashMap.get(item).size();
                item2No = item;
            }
        });

		//Priniting two items, count and orders
        System.out.println("Item " + item1No.getItemNo() +
                " -> usageCount:" + itemOrderHashMap.get(item1No).size() +
                " -> " + itemOrderHashMap.get(item1No).stream().map(item -> "Order " + item.toString() +", ").reduce("", String::concat));
        System.out.println("Item " + item2No.getItemNo() +
                " -> usageCount:" + itemOrderHashMap.get(item2No).size() +
                " -> " + itemOrderHashMap.get(item2No).stream().map(item -> "Order " + item.toString() +", ").reduce("", String::concat));
    }
}

class Order {
    Integer orderNo;
    List<Item> items = new ArrayList<>();

    public Order() {
    }

    public Order(Integer orderNo, List<Item> items) {
        this.orderNo = orderNo;
        this.items = items;
    }

    public Integer getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(Integer orderNo) {
        this.orderNo = orderNo;
    }

    public List<Item> getItems() {
        return items;
    }

    public void setItems(List<Item> items) {
        this.items = items;
    }

    @Override
    public String toString() {
        return "Order{" +
                "orderNo=" + orderNo +
                ", item=" + items +
                '}';
    }
}

class Item {
    Integer itemNo;

    public Item() {
    }

    public Item(Integer itemNo) {
        this.itemNo = itemNo;
    }

    public Integer getItemNo() {
        return itemNo;
    }

    public void setItemNo(Integer itemNo) {
        this.itemNo = itemNo;
    }

    @Override
    public String toString() {
        return "Item{" +
                "itemNo=" + itemNo +
                '}';
    }
}