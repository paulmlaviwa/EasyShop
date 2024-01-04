Welcome to EasyShop!
![easyShopHome2](https://github.com/paulmlaviwa/EasyShop/assets/146879419/76455ed3-8f87-4bcf-888a-df12bba154eb)

Capstone Overview:
  Easy Shop is an already existing e-commerce application for technology and electronics. Our team was tasked with releasing Version 2 by fixing bugs that had been reported by users, implementing and editing existing methods, and adding new features.

  Bugs: 
  1: Search Filter Bug- returned the incorrect results because it had the wrong ammount of prepared statements. We also wrote unit tests to guide us in solving the issue.![image](https://github.com/paulmlaviwa/EasyShop/assets/146879419/38f0be1a-b0b6-45aa-bd5c-24b794072812)
  
  2: Three Laptops- duplicate listings for the same product because the update products method was calling on CREATE instead of UPDATE, therefore it was adding a new listing when it should have been editing the already existing one.

 ![easyShopLogin1](https://github.com/paulmlaviwa/EasyShop/assets/146879419/c6990c86-720d-436a-9ec6-010c588e88a5)  
 
Implemented Methods: 
Categories Controller- getProductsById(by category Id), addCatgegory(Admin ONLY authorization), updateCategory(Admin ONLY authorization), deleteCategory(Admin ONLY authorization), 
MySQLCategories- getAllCategories(), getById(by category Id), createCategory(Admin ONLY authorization), updateCategory(uses the category Id), deleteCategory(uses the cat id)


New Features: 
Can't shop without a Shopping Cart! So we added one. 
![easyShopCart2](https://github.com/paulmlaviwa/EasyShop/assets/146879419/63c4c450-0c50-4d87-8ec0-4c7c97d4e3ec)

- Accessible to logged in users ONLY (@PreAuthorized for the roles Admin and User)
- Adding(PUT) to cart (update the database for the CURRENT user, and if that user logs out before purchasing the items will stay in their cart for when they return)
- Viewing the cart (it allows user to view all items in their cart from the cart screen including the quantity of each item.)
- Delete Items in Cart (clear the shopping cart and delete all of the current user's items form the shopping cart table)
  ![easyShopCart1](https://github.com/paulmlaviwa/EasyShop/assets/146879419/8371df4c-24bf-427e-9e30-3ed62e3940bf)


  Because the site was functional we were able to use it to test our changes. Seeing the code working was insightful as we added and reconfigured in the IDE. It allowed us to check and test the code in the moment.
  
Issues that we encountered as we worked: 
  1: Setting up/Getting Started- This project presented a challenge that we had not yet encountered as we had only worked with code we ourselves had written. Therefore we knew each piece and how it got there. Working with our code made it easier to find any bugs since we were familiar with it and could quickly pinpoint the issue. This project was slow starting because we had to spend time getting comfortable with a large ammount of code for an application we hadn't seen or worked with previously, but also gave us practice in something that will be a daily occurance in our careers.
  
  2: Postman- Had trouble with postman tests because while items were being added to the database's cart table they were not being added to the shoppingCart object so it was returning empty. Initially the addCart method was reurning a blank ShoppingCart object, instead of returning the new/updated cart. At first, we attempted to call the GetByUserId method, as it returns the current ShoppingCart that is saved in the database. However, this way resulted in many more bugs and did not work as intended. As a workaround, we inserted the logic of the method to circumvent the error. This is now returning the updated ShoppingCart, while still keeping the application working. Although the return is now fixed,  we are still having trouble on Postman. The error that we are receiving still states that our return is empty. (Side Note: The insert of the GetByUserId logic is a temporary fix and is a bad practice of duplicated code, thus is something we would like to see changed in the future).
  
  ![addCartReturnBugFix](https://github.com/paulmlaviwa/EasyShop/assets/14105717/f6770a82-cdbf-4383-b32c-08fad4771812)



  3: JavaScript-  Cart was not being loaded at the same time as the database, which would require a refresh to show new products in cart
Fixed by examining Javascript to locate the addToCart method. Noticed that addToCart was not updating the cart everytime something was added. Provided new code to addToCart that would throw the load method and update the cart everytime a new item was added
NEW
  Code Highlight:
![easyShopJSFix](https://github.com/paulmlaviwa/EasyShop/assets/146879419/82cc046e-a894-428f-838d-fd4b3e56373b)

Upcoming Features:
  <img width="3191" alt="Future_of_Easy_Shop" src="https://github.com/paulmlaviwa/EasyShop/assets/146879419/faecdf14-184c-4260-8051-7531ee3583a9">


 
