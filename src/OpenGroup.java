import java.util.ArrayList;


public class OpenGroup extends Group {

	private ArrayList<User> members;
	private ArrayList<Post> posts;

	public OpenGroup(String name, String info) {
		super(name, info);

		members = new ArrayList<User>();
		posts = new ArrayList<Post>();
	}

	//returns true if the current user is member of this group
	public boolean isMember(User user) {
		if(members.contains(user))
			return true;
		return false;
	}

	//adds the selected user if not a member
	public void addMember(User user) {
		if(isMember(user)){
			System.out.println(user.getName()+" is already a member!");
			return;
		}

		members.add(user);
		user.setGroup(this);
	}

	public void printMembers() {
		System.out.println("********************************");
		System.out.println("Members of group "+this.getName());
		System.out.println("********************************");

		int counter = 1;
		for(User member : members) {
			System.out.println(counter+": "+member.getName());
			counter++;
		}
	}

	//returns true if the current user can add a post in this group
	public boolean canAddPost(User user) {
		if(isMember(user))
			return true;
		else {
			for(User friend : user.getFriends()) {
				if(friend.getGroups().contains(this))
					return true;
			}
		}
		return false;
	}

	//adds a post in the selected group
	public void addPost(Post post) {
		if(canAddPost(post.getUser())) //only if the user can add a post
			posts.add(post);
		else
			System.out.println("User " + post.getUser() + "is not a member nor his friends.");
	}

	//adds a reply to a selected post
	public void addReplyToPost(Post post, Post reply) {
		if(canAddPost(reply.getUser())) //only if the user can add a reply
			post.setReply(reply);
		else
			System.out.println("User " + reply.getUser() + "is not a member nor his friends.");
	}

	public void printWall() {
		System.out.println("Group " + super.getName() + " wall");
		for (Post post : posts){
			post.printPost();
		}
	}

	public String getLatestPost() {
		//We can't find the last element from an ArrayList. so....
		int lastindx = posts.size() - 1; //we find first the size of the Array and extract 1
		Post lastPost = posts.get(lastindx); //so this is the last element!

		if(lastPost.getReply() != null) {
			String lpStr = lastPost.toString() + lastPost.printAllReplies();
			return lpStr;
		}
		return lastPost.toString();
	}

}
