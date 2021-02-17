# Social Media Data Analytics Platform
<img src="img/output/social_media.png" />

Social Media Data Analytics Platform simulates social media data emission from various devices (web, mobile and tablets) and handles all the real-time data involved to perform BI analytics on the raw data stored in the MySQL database

### Data emission simulation
The producer is a multiprocessing (potentially also distributed) application which randomly create users and simulate interactions with the social media app.
It should emit sign-up message to Kafka for new users and should simulate a random amount of event such as likes, comments, friend requests, friend acceptance. 

#### Types of events
Here are the events messages that will be store in MySQL
- sign-ups
- friend requests
- friend requests acceptances
- pictures
- videos

### Data Analytics Architecture

<kbd>
    <img src="img/output/overview.png" />
</kbd>
