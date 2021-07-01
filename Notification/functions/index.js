'use strict'


const functions = require('firebase-functions');

const admin = require('firebase-admin');

admin.initializeApp(functions.config().firebase);




exports.sendNotification = functions.database.ref('/notificationref/{id}/{notification_id}')
.onWrite((data, context) =>
{
	const id = context.params.id;
	const notification_id = context.params.notification_id;


	console.log('We have a notification to send to :' , id);


	if (!data.after.val()) 
	{
		console.log('A notification has been deleted :' , notification_id);
		return null;
	}

	

	const fromuser=admin.database().ref(`/notificationref/${id}/${notification_id}`).once('value');
	return fromuser.then(fromuserresult=>{
		const fromuserid=fromuserresult.val().from;


		console.log('you have new notification from :' , fromuserid);

		const userquery=admin.database().ref(`donor/${fromuserid}/firstName`).once('value');
		return userquery.then(userresult=>{
			const username=userresult.val();


		const food=admin.database().ref(`/notificationref/${id}/${notification_id}`).once('value');
		return food.then(fooddescription=>{
			const fooddescp=fooddescription.val().fooddetail;
			const foodtime=fooddescription.val().fooddescp;
			const foodquan=fooddescription.val().Quantity;
			const DeviceToken = admin.database().ref(`/ngo/${id}/devicetoken`).once('value');

	return DeviceToken.then(result => 
	{
		const token_id = result.val();

		const payload = 
		{
			notification:
			{
				title: "New donation Request",
				body: `${username} has send you a new donation Request.Food description: ${fooddescp}food type:${foodtime} Quantity:${foodquan}`,
				icon: "default",
				click_action:"com.example.rotibanksystem_TARGET_NOTIFICATION"


			}
		};





		return admin.messaging().sendToDevice(token_id, payload)
		.then(response => 
			{
				console.log('This was a notification feature.');
			});

		
	});




		});
			
		});
		
	});
});





exports.sendacceptnotification = functions.database.ref('/notificationref/{id}/{notification_id}')
.onWrite((data, context) =>
{
	const id = context.params.id;
	const notification_id = context.params.notification_id;


	console.log('We have a notification to send to :' , id);


	if (!data.after.val()) 
	{
		console.log('A notification has been deleted :' , notification_id);
		return null;
	}


			const fromuser = admin.database().ref(`/notificationref/${id}/${notification_id}`).once('value');

	return fromuser.then(fromuserresult=>{
		const fromuserid=fromuserresult.val().from;
		console.log(fromuserid);

		const userquery=admin.database().ref(`donor/${fromuserid}/devicetoken`).once('value');
		return userquery.then(userresult=>{
			const token_id = userresult.val();

		const payload = 
		{
			notification:
			{
				title: "New donation Request",
				body: `Request accepted`,
				icon: "default"
				
			}
		};

		return admin.messaging().sendToDevice(token_id, payload)
		.then(response => 
			{
				console.log('This was a notification feature.');
			});
	});
});

});




exports.senddeclinenotification = functions.database.ref('/notificationref/{id}/{notification_id}')
.onWrite((data, context) =>
{
	const id = context.params.id;
	const notification_id = context.params.notification_id;


	console.log('We have a notification to send to :' , id);


	if (!data.after.val()) 
	{
		console.log('A notification has been deleted :' , notification_id);
		return null;
	}


			const fromuser = admin.database().ref(`/notificationref/${id}/${notification_id}`).once('value');

	return fromuser.then(fromuserresult=>{
		const fromuserid=fromuserresult.val().from;
		console.log(fromuserid);

		const userquery=admin.database().ref(`donor/${fromuserid}/devicetoken`).once('value');
		return userquery.then(userresult=>{
			const token_id = userresult.val();

		const payload = 
		{
			notification:
			{
				title: "New donation Request",
				body: `Request declined`,
				icon: "default"
				
			}
		};

		return admin.messaging().sendToDevice(token_id, payload)
		.then(response => 
			{
				console.log('This was a notification feature.');
			});
	});
});

});







