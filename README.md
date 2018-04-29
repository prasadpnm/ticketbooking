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
  


