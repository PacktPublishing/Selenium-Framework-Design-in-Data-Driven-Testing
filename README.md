# Selenium Framework Design in Data-Driven Testing

This is the code repository for [React Native Blueprints](https://www.packtpub.com/web-development/react-native-blueprints?utm_source=GitHub&utm_medium=repo&utm_campaign=9781787288096), published by [Packt](https://www.packtpub.com). It contains all the supporting project files necessary to work through the book from start to finish.

## About the Book

This project-based guide takes you through 8 projects to help you gain a sound understanding of the framework when it comes to building mobile apps with native user experience. You’ll begin with building simple applications such as a groceries list app, and an RSS Reader app then move on to advanced apps such as an image sharing app, a guitar tuner app, a game, and more. You’ll build applications that cover the core APIs, UI, components, views, navigations, and more.

By the end of the book, you’ll be able to build professional Android and iOS applications using React Native.

## Instructions and Navigations
All of the code is organized into folders. Each folder starts with a number followed by the application name. For example, Chapter01.



The code will look like the following:
```

/*** src/main.js ***/
import React from 'react';
import { StackNavigator } from 'react-navigation';
import ShoppingList from './screens/ShoppingList.js';
import AddProduct from './screens/AddProduct.js';
const Navigator = StackNavigator({
  ShoppingList: { screen: ShoppingList },
  AddProduct: { screen: AddProduct }
});

```

## Related Products
* [React Native By Example](https://www.packtpub.com/application-development/react-native-example?utm_source=GitHub&utm_medium=repo&utm_campaign=9781786464750)

* [React Native - Building Mobile Apps with JavaScript](https://www.packtpub.com/application-development/react-native-building-mobile-apps-javascript?utm_source=GitHub&utm_medium=repo&utm_campaign=9781787282537)

* [Getting Started with React Native](https://www.packtpub.com/application-development/getting-started-react-native?utm_source=GitHub&utm_medium=repo&utm_campaign=9781785885181)








