

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
