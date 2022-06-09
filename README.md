# DevTest API

This simple API is designed for a CRM solution.

## Requisites

* The API should be only accessible by a registered user by providing an authentication mechanism.
* Admin can List/Create/Update/Delete users and change Admin status.
* Users can:
		
> * list customers.
> * get customer's info.
> * create customers from CSV file.
> * upload photo.
> * update customer's info and delete customer.

## Technological stack

As the first approach of a solution, the stack definition is:

* Java
* Springboot
* MyBatis (Persistence)
* Swagger (Docu)
* Okta (Auth)
* MySQL (Data)
* Apache commons CSV (CSV File)
* SendGrid/STMP Free (Mailing)
* AWS?? (Deployment)

## Solution

First steps to configure the solution is with the okta-cli installed. We can simply run `okta start spring-boot` and we create a new okta project and new okta account and authserver.

Then, the okta communication, redirect-uri, client-id and secret-id are all configured, so we can start the API.

## API

We need two separates controller. Admin(Users management) and Customer(Customers management)

## How to run the app

> * under construction

## How to clone the project

> * under construction

## how to deploy project

> * under construction