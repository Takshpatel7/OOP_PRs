
        if (n<=availableseats)
        {
            availableseats-= n;
            booked+= n;
            return "Booked";
        } 
        else 
        {
            return "Not Available";
        }
    