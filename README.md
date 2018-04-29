# Ticket Booking Application.

Setup Instructions.

-checkout the code and execute mvn clean install.
-Setup database copy db file in your local home.  if you want to copy any other place,please change the spring boot properties accordingly.


Execution commands.


Seat Assignment Order.

1. First it will try to allocate all the seats continiosly and close to venue.
    -if the group size is more than half row size. it will try to allocate seats from left to right in first available row.
    -if the group size is small it first allocate seats from middle to left, once first half of the row filled then it will fill second 
      half from middle to right.
 2. if the group seats not available continiously in any of the row's then it will start allocate the seats in multiple rows starting from     first row to last row.


