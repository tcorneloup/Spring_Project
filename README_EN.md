Project Spring

For this project, I created a subscription management system for persist some information :

Subscription :
- type of subscription (FIX, MOBILE, FIBER), 
- date of subscription, 
- client ID

a subscription can have one or more options: 
- name (ROAMING, NETFLIX, MUSIC, HD) 
- date of subscription.

To manage those subscriptions, I created 3 routes :

- One to get all the subscriptions with their options.
- One to add an option to an existing subscription.
- One to create a subscription

Specifics Rules :
- Option ROAMING can be only add to a MOBILE subscription
- Option NETFLIX can be only add to a FIBRE subscription
- Option HD option can only be added if a NETFLIX option already exists on the subscription.
- Option MUSIC can be added to MOBILE or FIBER subscriptions
 WARNING : A subscription cannot have the same option twice

The goal of this exercise is to see how the Spring framework work.

The global architecture follows the main principles of DDD (Domain Driven Design)
Tests cover 95% of the code