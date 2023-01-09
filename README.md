## Milestone 2 POO TV - Robert Vulpe, 323CD

### Short description about main functionalities and modifications from the previous milestone
This milestone assumes the implementation of several new actions that can be performed by users and
the administrator of the streaming service. The newly introduced actions are: `subscribe`, `back`, 
`database add or delete` and `premium user recommendation`. The first two are performed by users. 
When a user subscribes to a genre of a movie, he will be notified automatically when a new movie 
is added to the platform or when an existing movie is deleted, if the subscribed genre is among the 
genres of this movie. The `back` action is a UI feature that enables a user to navigate to previous 
visited pages. At the end of all actions, if a premium user is connected, it will receive 
a recommendation of a movie that isn't watched yet, according to his preferred genres.

From the last milestone, I chose to separate the default methods in the `Visitable` interface and to 
add them into a new interface called `OutputParser`. This way, I can use them on other contexts through 
inheritance, avoiding rewriting the same implementations in other interfaces. Also, I created a class 
with constants for the platform, called `PlatformConstants`.

## Design details
For making all the changes described above, I started by implementing the functionalities for the newly 
added actions. At the beginning, I modified the logic from the previous milestone, using the ***Strategy*** 
pattern to dynamically select an action at runtime. After that, the execution of each strategy is based on 
the ***Command*** pattern, where an invoker (in this case the `ActionsWrapper` object) runs the specified 
command according to the provided details about the current action. I chose to use these two patterns together 
because I think it's the best way to combine the advantages of both models. Either way, I could pick a *Singleton* 
for the `Platform` instance for example instead of using one of these patterns, but the fact that the `Platform` 
needs to be instantiated again when a different configuration is loaded from input could cause issues to this 
approach and I opted for the first option. 

To implement the notification feature, I used the ***Observer*** pattern, where all users that subscribe to 
a movie's genre are the observers and the `Platform` is the observable object, modifying its state when a new 
movie is added or an existing movie is deleted. For the recommendation, I implemented the algorithm described 
in the wiki page, using a *TreeMap* to maintain the preferred genres of a premium user, because it provides an 
efficient method of storing key-value pairs in sorted order. Then, I queried the entries, looking for a movie 
of whose genres contain the most liked genre by the user.

If you want to see better the relations and links between the classes that I defined to complete the entire  
project, take a look at *diagram.png* file. 

## Conclusions
Finally, I think it was a great challenge to complete both milestones. It helped me understand what object-oriented 
programming really means, providing a starting point for other future projects. 
