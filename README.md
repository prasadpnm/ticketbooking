# Ticket Booking Application.

Setup Instructions.

 1 checkout the code and execute mvn clean install.
 2 Setup database copy db file in your local home.  if you want to copy any other place,please change the spring boot properties   accordingly.


Execution commands.


Seat Assignment Order.

1. First it will try to allocate all the seats continiosly and close to venue.
    -if the group size is more than half row size. it will try to allocate seats from left to right in first available row.
    -if the group size is small it first allocate seats from middle to left, once first half of the row filled then it will fill second 
      half from middle to right.
 2. if the group seats not available continiously in any of the row's then it will start allocate the seats in multiple rows starting from     first row to last row.
 
 
 Implementaion Details :
  
  1.Rest API implemented using Spring boot modules(JPA,WEB and testing.)
  2.Used H2 database in local mode. (this can be replaced with any enterprise level databases like mysql or postgress)
  3.Seat allocation logic tested with Junit and spring boot test modules.
  4.Client is implemented in java and integrated with maven phaces.
  5. i used default program code for further development. right now it is hard coded for only one program.
  6. logging is implemented default i configured java temp directory. it can be changed app properties file.
  7.Global exception handling implemented for rest controller.
  8.Spring JPA and JPA repositories is used to interact the H2 database.
  9.in memory session handling implemented. right now it is hardcoded for 5 minutes. it can be changed.
  
  
 API details  :
 
   
   1.Seat Availability 
   this method need to be executed at least once before reserve or hold the seats.
   
   
     
     curl -X GET http://localhost:2003/noOfSeatsAvlble -H 'Accept: application/json' -H 'Cache-Control: no-cache' -H 'Content-Type: application/json' -H 'Postman-Token: 53514111-6beb-4844-812e-be971d886080' 
  
  2. Seat finder.
  
    curl -X POST http://localhost:2003/reservseats -H 'Accept: application/json' -H 'Cache-Control: no-cache' -H 'Content-Type: application/json' -H 'Postman-Token: bc921be1-5c43-44c6-af6d-5e782518cbd7' -d '{
	"numberOfSeats": 10,
	"email" :"prasad199.msc@gmail.com"}'



  3. confirmSeat resrvation

    curl -X POST  http://localhost:2003/conformReservation -H 'Accept: application/json' -H 'Cache-Control: no-cache' -H 'Content-Type: application/json' -H 'Postman-Token: 8dce3498-b7dd-43c9-bc71-4a8262525333' -d '{
	"email" :"prasad1.msc@gmail.com"}'
  


