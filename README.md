# Ticket Booking Application.

Setup Instructions.

1. git clone https://github.com/prasadpnm/ticketbooking.git
 2. cd ticketbooking 
 3. mvn clean install
 4. update the src/main/resources/application.properties file 
      update the database path property 'C:/Users/prasa/Documents/GitHub' with your local path from where you are executing git clone command. 
      https://github.com/prasadpnm/ticketbooking/blob/master/src/main/resources/application.properties#L17
  
 5. mvn spring-boot:run
 6. open another terminal window and execute the curl commands as below.
 7. you can find log files in java temp directory, you can change the location by updating below property.
    https://github.com/prasadpnm/ticketbooking/blob/master/src/main/resources/application.properties#L6

  
 API details  :
 
   1.Seat Availability  -
   this method need to be executed at least once before reserve or hold the seats.
   
   
     
     curl -X GET http://localhost:2003/noOfSeatsAvlble -H 'Accept: application/json' -H 'Cache-Control: no-cache' -H 'Content-Type: application/json' -H 'Postman-Token: 53514111-6beb-4844-812e-be971d886080' 
  
  2. Best available seat finder :
  
    curl -X POST http://localhost:2003/reservseats -H 'Accept: application/json' -H 'Cache-Control: no-cache' -H 'Content-Type: application/json' -H 'Postman-Token: bc921be1-5c43-44c6-af6d-5e782518cbd7' -d '{
	"numberOfSeats": 10,
	"email" :"prasad199.msc@gmail.com"}'


  3. conform seat resrvation :
      use the same email id used in 2nd step. this can be executed within 5 minutes after executing 2nd step

    curl -X POST  http://localhost:2003/conformReservation -H 'Accept: application/json' -H 'Cache-Control: no-cache' -H 'Content-Type: application/json' -H 'Postman-Token: 8dce3498-b7dd-43c9-bc71-4a8262525333' -d '{
	"email" :"prasad199.msc@gmail.com"}'
	
Seat Assignment Order.

1. First it will try to allocate all the seats continiosly and close to venue.
    -if the group size is more than half of a row size. it will try to allocate seats from left to right in first available     row.
    -if the group size is small it first allocate seats from middle to left, once first half of the row filled then it will fill second half from middle to right.
 2. if the seats not available continiously in any of the row's then it will start allocate the seats in multiple rows     starting from  first row to last row.
 
 
 Implementaion Details :
  
 1. Rest API implemented using Spring boot modules(JPA,WEB and testing.)
 2. Used H2 database in local mode. (this can be replaced with any enterprise level databases like mysql or postgress)
 3. Seat allocation logic tested with Junit and spring boot test modules.
 4. i used default program code for further development. right now it is hard coded for only one program.
 5. logging is implemented default i configured java temp directory. it can be changed app properties file.
 6. Global exception handling implemented for rest controller.
 7. Spring JPA and JPA repositories is used to interact the H2 database.
 8. in memory session handling implemented. right now it is hardcoded for 5 minutes. it can be changed.
  
this solution right now works using client email id as key,so at any given time one customer can hold only one request.this can be replaced using session Id as key.


