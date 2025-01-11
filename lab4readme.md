Monisha Prasad Lab 4 - Read Me

1. I have chosen to work on the Grill Order Management system for the Commons at my college because of my firsthand experience with its operations. Working there has given me insights into the existing workflow, and I believe there is room for improvement in terms of speed, efficiency, and user accessibility. By developing a more streamlined and intuitive application for managing grill orders, I aim to enhance both student satisfaction and employee productivity. This project presents an excellent opportunity for me to apply my skills in software development, while tackling real-world challenges. Additionally, creating a system that could be implemented and utilized by the college adds a layer of practical significance to this project, allowing me to contribute something tangible to my institution. This experience will also deepen my knowledge in areas like application design, system optimization, and real-time functionality, which are crucial to my professional growth.

2. In addition to the Order entity, the **Menu** and **OrderDetails** entities are crucial for a comprehensive grill order management system.

The **Order** entity handles high-level details such as customer information, order status, and total cost. 
The **Menu** entity represents individual items like burgers, sandwiches, fries, and hotdogs, including their attributes like price, description, and availability. 
**OrderDetails** serves as a bridge between Order and Menu, detailing which items are included in each order, their quantities, and any special instructions. 

This structure enables the system to track the relationship between customer orders and the menu items they include, ensuring accurate order processing and reporting.

8.

  1. Testing the create order function

  Code Block:
  @Test
  public void createTest() {
        GrillOrder grillOrder = new GrillOrder("John Doe", 202l, 3, 150l,ItemStatus.FINISHED);
        tx.begin();
        em.persist(grillOrder);
        tx.commit();   
        GrillOrder readBackFromDatabase = em.find(GrillOrder.class, grillOrder.getOrderId() );
        assertNotNull(readBackFromDatabase);
        assertTrue(readBackFromDatabase.getOrderId() > 0);
        assertEquals("John Doe", readBackFromDatabase.getCustomerName());
    }

  Output:
  Test Create Order: PASSED

  Created a new order
  Committed the transaction to the database.
  Read the Order from the database using orderID
  Checked for NotNull
  Verified if it's the same order that we created

  2. Testing the read order function
  
  Code Block:
  @Test 
    public void readTest(){
        GrillOrder grillOrder = new GrillOrder("John Doe", 202l, 3, 150l,ItemStatus.FINISHED);
        tx.begin();
        em.persist(grillOrder);
        tx.commit(); 
        GrillOrder readBackFromDatabase = em.find(GrillOrder.class, grillOrder.getOrderId() );
        assertNotNull(readBackFromDatabase);
        assertEquals("John Doe", readBackFromDatabase.getCustomerName());
        assertEquals(202, readBackFromDatabase.getItemId());
        assertEquals(3, readBackFromDatabase.getQuantity());
        assertEquals(150, readBackFromDatabase.getTotalPrice());
        assertEquals(ItemStatus.FINISHED, readBackFromDatabase.getItemStatus());
        tx.begin();
        em.remove(grillOrder);
        tx.commit();
    }  

  Output:
  Test Read Order: PASSED

  Created a new order
  Committed the transaction to the database.
  Read the Order from the database using orderID
  Checked for NotNull
  Verified each property separately it's the same order that we created
  Deleted the created order to avoid redundancy


  3. Testing the update order function
  
  Code Block:
  @Test 
    public void updateTest() {
        GrillOrder go = em.createQuery("select o from GrillOrder o where o.customerName = 'Test Name'",GrillOrder.class).getSingleResult();
        assertTrue(go.getOrderId()>0);
        tx.begin();
        **go.setQuantity(5);**
        tx.commit();
        GrillOrder readBackFromDatabase = em.createQuery("select o from GrillOrder o where o.customerName = 'Test Name'",GrillOrder.class).getSingleResult();
        assertEquals(go.getOrderId(), readBackFromDatabase.getOrderId());
        assertEquals(5, readBackFromDatabase.getQuantity());
    }

  Output:
  Test Update Order: PASSED
  
  Created a new order with quantity set to 2 in the beforeAll
  Read the Order from the database using customer name
  Updated the quantity to 5 using go.setQuantity(5);
  Committed the transaction to the database.
  Read the Order from the database using customer name
  Verified if the quantity is updated to 5

4. Testing the delete order function
  
  Code Block:
  @Test 
    public void deleteTest(){
        GrillOrder grillOrder = new GrillOrder("John", 202l, 3, 150l,ItemStatus.FINISHED);
        tx.begin();
        em.persist(grillOrder);
        tx.commit();
        tx.begin();
        em.remove(grillOrder);
        tx.commit();
        int count = em.createQuery("select o from GrillOrder o where o.customerName = 'John'",GrillOrder.class).getResultList().size();
        assertEquals(0, count);
    }

  Output:
  Test Delete Order: PASSED
  
  Created a new order
  Committed the transaction to the database.
  Deleted the created order
  Committed the transaction to the database.
  Verified if the count of the orders to be 0

  5. Testing the order validation
  
  Code Block:
  @Test 
    public void orderIsValid() {
        GrillOrder go = new GrillOrder("Test Name", 202l, 3, 150l,ItemStatus.FINISHED);
        System.out.println("actorIsValid: " + go.toString());
        Set<ConstraintViolation<GrillOrder>> violations = validator.validate(go);
        assertEquals(0,violations.size());
    }

  Output:
  Test Valid Order: PASSED
  
  Checked for any violations in the order

 6. Testing the customer name validation - **Negative**
  
  Code Block:
  @Test
    public void invalidCustomerName() {
        GrillOrder go = new GrillOrder("", 202l, 3, 150l,ItemStatus.FINISHED);
        System.out.println("orderIsInvalid: " + go.toString());
        Set<ConstraintViolation<GrillOrder>> violations = validator.validate(go);
        ConstraintViolation<GrillOrder> violation = violations.iterator().next();
        System.out.println("The violation is : " + violation.getMessage());
        assertEquals(1,violations.size());
    }

  Output:
  Test Valid Customer Name: PASSED
  
  Validated that there is 1 violation indicating a blank customer name

  7. Testing the customer name validation - **Positive**
  
  Code Block:
  @Test 
    public void validCustomerName() {
        GrillOrder go = new GrillOrder("John", 202l, 3, 150l,ItemStatus.FINISHED);
        System.out.println("orderIsInvalid: " + go.toString());
        Set<ConstraintViolation<GrillOrder>> violations = validator.validate(go);
        ConstraintViolation<GrillOrder> violation = violations.iterator().next();
        System.out.println("The violation is : " + violation.getMessage());
        assertEquals(0,violations.size());
    }

  Output:
  Test Valid Customer Name: PASSED
  
  Validated that there is 0 violation indicating a no blank customer name
  
  
