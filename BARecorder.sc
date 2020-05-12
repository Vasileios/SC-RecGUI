BARecorder{
	classvar <>server;
	var name;

	//Constructor
	*new{
		|n|
		var obj;
		server = Server.local;
		obj = super.new;
		obj.init(n);
		^obj;
		
	}
	init{|n|

var w, channels, formats, samples, popupCh, popupF, popupS, syn1, syn2, compAction1, compAction2, compAction3, compAction4, compAction5, compAction6, compAction7, busses1, busses2, popupS_Ch1, popupS_Ch1_2, popupS_Ch2, v, i, k;

	w = Window.new("BARecorder", Rect(400, 200, 600, 600)).front;
		v = CompositeView(w,Rect(5,5,380,380)).background_(Color.black);
			//.rand(0.7));
    v.decorator = n = FlowLayout(v.bounds, margin: 0@0, gap: 5@5);
		i = CompositeView(w,Rect(240,5,340,380)).background_(Color.white);
		//		.rand(0.7));
    i.decorator = n = FlowLayout(i.bounds, margin: 0@0, gap: 5@5);

	~path = Platform.userHomeDir +/+ "Desktop/tests/";

	~filePrefix = "SC_RecGui_";
	~timeStamp = Date.localtime.stamp;
	~recordPath = ~path +/+ ~filePrefix ++ ~timeStamp;	
	////set up arrays of syths, buffers and buses

	~arr = 10.do{|i=1|  i+1};
	

	~syns = ~arr.collect{|item| "~syns".asString++item};//create a list of syns_names

	~buffer = ~arr.collect{|item| "~buffer".asString++item};//create a list of buf_names.
	~bus = ~arr.collect{|item| "~bus".asString++item};//create a list of bus_names.
	~busC = ~arr.collect{|item| "~busC".asString++item};//create a list of bus_names.
		
//~recordPath = ~path +/+ ~filePrefix ++ ~timeStamp ++ "." ++ s.recHeaderFormat;
///Popup menu channels
popupCh = PopUpMenu(w, Rect(10, 10, 90, 20))//number of channels
    .items_(["8","6", "4" , "2" , "1"]);
    channels = [ //channels
		{ server.recChannels = 8; "Channels 8".postln},
		{ server.recChannels = 6; "Channels 6".postln},
        { server.recChannels = 4; "Channels 4".postln},
        { server.recChannels = 2; "Channels 2".postln},
        { server.recChannels = 1; "Channels 1".postln}
	
	];

	//~bufer[0]=Buffer.alloc(s,1024,8)
	//~bufer[0]=Buffer.alloc(s,1024,6)
///Popup menu audio Fomats

//audio file Formats
popupF = PopUpMenu(w, Rect(10, 60, 90, 20))
    .items_(["AIFF", "WAV"]);
formats = [ 
	{server.recHeaderFormat = "AIFF";
		~recordPath = ~recordPath ++ ".aif";
	}, 
	{server.recHeaderFormat = "WAV";
		~recordPath = ~recordPath ++ ".wav";
	}

];
popupS_Ch1_2 = PopUpMenu(w, Rect(250, 60, 60, 20))
    .items_(["bus1", "bus2", "bus3", "bus4", "bus5", "bus6", "bus7", "bus8"]);
busses1 = [ 
	{ In.ar(~bus[0], 2);
	}, 
	{ In.ar(~bus[1], 2);
	}, 
	{In.ar(~bus[2], 2); 
	}, 
	{In.ar(~bus[3], 2);
	}, 
	{In.ar(~bus[4], 2);
	}, 
	{In.ar(~bus[5], 2);
	}, 
	{In.ar(~bus[6], 2);
	}, 
	{In.ar(~bus[7], 2);
	}

];
popupS_Ch1 = PopUpMenu(w, Rect(380, 60, 60, 20))
    .items_(["bus1", "bus2", "bus3", "bus4", "bus5", "bus6", "bus7", "bus8"]);
busses1 = [ 
	{ In.ar(~bus[0], 2);
	}, 
	{ In.ar(~bus[1], 2);
	}, 
	{In.ar(~bus[2], 2); 
	}, 
	{In.ar(~bus[3], 2);
	}, 
	{In.ar(~bus[4], 2);
	}, 
	{In.ar(~bus[5], 2);
	}, 
	{In.ar(~bus[6], 2);
	}, 
	{In.ar(~bus[7], 2);
	}

];
popupS_Ch2 = PopUpMenu(w, Rect(470, 60, 60, 20))
    .items_(["bus1", "bus2", "bus3", "bus4", "bus5", "bus6", "bus7", "bus8"]);
busses2 = [ 
{ In.ar(~bus[0], 2);
	}, 
	{ In.ar(~bus[1], 2);
	}, 
	{In.ar(~bus[2], 2); 
	}, 
	{In.ar(~bus[3], 2);
	}, 
	{In.ar(~bus[4], 2);
	}, 
	{In.ar(~bus[5], 2);
	}, 
	{In.ar(~bus[6], 2);
	}, 
	{In.ar(~bus[7], 2);
	}

];

///Popup menu sampleFormat
//resolution in bits..bit per sample//To enable resolution other than the default you need to do Server.local.recSampleFormat_("int16") and to recompile class library
	~recSampleFormat = ServerOptions.new;
	~currentSampleFormat = "int24";
	~recSampleFormat.recSampleFormat_(~currentSampleFormat);
	~samleFormat32 = "int32";
	~samleFormat24 = "int24";
	~samleFormat16 = "int16";
	~samleFormat8 = "int8";
	popupS = PopUpMenu(w, Rect(10, 120, 90, 20))
    .items_(["32bit", "24bit", "16bit", "8bit"]);
samples = [
	{//setting up int32 sample format
		~currentSampleFormat = "int32";
		Server.local.recSampleFormat_(~currentSampleFormat);
	 "Recording in :".postln; ~recSampleFormat.recSampleFormat_(~currentSampleFormat).postln;
		"Warning: you might need to recompile the server for the server's options to take effect".postln;
	},
	
	{//setting up int24 sample format
		~currentSampleFormat = "int24";
		Server.local.recSampleFormat_(~currentSampleFormat);
	 "Recording in :".postln; ~recSampleFormat.recSampleFormat_(~currentSampleFormat).postln;
		"Warning: you might need to recompile the server for the server's options to take effect".postln;
	},
	

	/*{p = ServerOptions.new;p.recSampleFormat = "int16".postln;Server.local.recSampleFormat = "int16";"Warning: reboot scsynth for options to take effect"},
	*/

	{//setting up int16 sample format
		~currentSampleFormat = "int16"; //4.wait;s.reboot;s.doWhenBooted{~recordGui.value};
	Server.local.recSampleFormat_(~currentSampleFormat);
		 "Recording in :".postln; ~recSampleFormat.recSampleFormat_(~currentSampleFormat).postln;
		 	"Warning: you might need to recompile the server for the server's options to take effect".postln;
	},	

	{//setting up int8 sample format
		~currentSampleFormat = "int8";
		Server.local.recSampleFormat_(~currentSampleFormat);
		"Recording in :".postln; ~recSampleFormat.recSampleFormat_(~currentSampleFormat).postln;
"Warning: you might need to recompile the server for the server's options to take effect".postln; "Warning: for sampleFormat int8 use AIFF header format".postln;}
	//thisProcess.recompile;
];

//Static_Txt--->channels
    ~textCh = StaticText (w , Rect (200, 175, 100, 20))
        .string_("Channels")
        .font_(Font("Times New Roman", 14))
        .align_(\center)
        .background_(Color.gray)
        .stringColor_(Color.black);
    ~textCh.bounds_(Rect(10, 30, 60, 20));
//Static_Txt--->formats
    ~textF = StaticText (w , Rect (200, 175, 100, 20))
        .string_("Formats")
        .font_(Font("Times New Roman", 14))
        .align_(\center)
        .background_(Color.gray)
        .stringColor_(Color.black);
    ~textF.bounds_(Rect(10, 80, 50, 20));
//Static_Txt--->sample
    ~textS = StaticText (w , Rect (200, 175, 100, 20))
        .string_("sample")
        .font_(Font("Times New Roman", 14))
        .align_(\center)
        .background_(Color.gray)
        .stringColor_(Color.black);
    ~textS.bounds_(Rect(10, 140, 50, 20));
//Static_Txt--->sample
    ~textComp = StaticText (w , Rect (200, 175, 100, 20))
        .string_("Compressor")
        .font_(Font("Times New Roman", 14))
        .align_(\center)
        .background_(Color.blue)
        .stringColor_(Color.white);
    ~textComp.bounds_(Rect(250, 6, 80, 20));
//Static_Txt--->side-chain comp
    ~textSchComp = StaticText (w , Rect (300, 175, 100, 20))
        .string_("Side-chain Compressor")
        .font_(Font("Times New Roman", 14))
        .align_(\center)
        .background_(Color.new255(66,88,24))
        .stringColor_(Color.white);
    ~textSchComp.bounds_(Rect(380, 6, 140, 20));

//Static_Txt--->thres
    ~textthres = StaticText (w , Rect (200, 175, 100, 20))
        .string_("thres")
        .font_(Font("Times New Roman", 12))
        .align_(\center)
        .background_(Color.red)
        .stringColor_(Color.white);
    ~textthres.bounds_(Rect(300, 100, 25, 20));
//Static_Txt--->slbelow
    ~textslbelow = StaticText (w , Rect (200, 175, 100, 20))
        .string_("slbelow")
        .font_(Font("Times New Roman", 12))
        .align_(\center)
        .background_(Color.red)
        .stringColor_(Color.white);
    ~textslbelow.bounds_(Rect(300, 140, 40, 20));
//Static_Txt--->slabove
    ~textslabove = StaticText (w , Rect (200, 175, 100, 20))
        .string_("slabove")
        .font_(Font("Times New Roman", 12))
        .align_(\center)
        .background_(Color.red)
        .stringColor_(Color.white);
    ~textslabove.bounds_(Rect(300, 180, 40, 20));
//Static_Txt--->attack
    ~textattack = StaticText (w , Rect (200, 175, 100, 20))
        .string_("attack")
        .font_(Font("Times New Roman", 12))
        .align_(\center)
        .background_(Color.red)
        .stringColor_(Color.white);
    ~textattack.bounds_(Rect(300, 230, 40, 20));
//Static_Txt--->release
    ~textrelease = StaticText (w , Rect (200, 175, 100, 20))
        .string_("release")
        .font_(Font("Times New Roman", 12))
        .align_(\center)
        .background_(Color.red)
        .stringColor_(Color.white);
    ~textrelease.bounds_(Rect(300, 270, 40, 20));
	//Static_Txt--->ampB
    ~textamp = StaticText (w , Rect (200, 175, 100, 20))
        .string_("amp")
        .font_(Font("Times New Roman", 12))
        .align_(\center)
        .background_(Color.red)
        .stringColor_(Color.white);
    ~textamp.bounds_(Rect(300, 310, 40, 20));
//Static_Txt--->mix
    ~textmix = StaticText (w , Rect (200, 175, 100, 20))
        .string_("mix")
        .font_(Font("Times New Roman", 12))
        .align_(\center)
        .background_(Color.red)
        .stringColor_(Color.white);
    ~textmix.bounds_(Rect(300, 350, 40, 20));
		// //Stethoscope
	//alloc buffers
	~buffer[0] = Buffer.alloc(server, 1024, 2);

	//create busses 
		~bus[0] = Bus.audio(server,1);
		~bus[1] = Bus.audio(server,1);
		~bus[2] = Bus.audio(server,1);
		~bus[3] = Bus.audio(server,1);
		~bus[4] = Bus.audio(server,1);
		~bus[5] = Bus.audio(server,1);
		~bus[6] = Bus.audio(server,1);
		~bus[7] = Bus.audio(server,1);
	
	//set up scope
	~scope = ScopeView(w, Rect(10,30,200,900),server);
	~scope.bufnum = ~buffer[0].bufnum;
	//~scope.channels_(8);
	~scope.server = server;
	~scope.bounds_(Rect(5, 380, 580, 155));
    //~scope.active_(true);
    ~scope.front;
    ~scope.background_(Color.black(0.15));

	

	
	//
	~busC[0] = Bus.control(server,1);
	
		~syn1 = SynthDef("test1", { |bus, bufnum|
                var z;
                z = In.ar(bus, 8);
                // ScopeOut2 writes the audio to the buffer
                // IMPORTANT - ScopeOut2, not ScopeOut
                ScopeOut2.ar(z, bufnum);
                Out.ar(0,z);
            }).play(
                RootNode(server),
                [\bus,~bus, \bufnum, ~buffer[0].bufnum],
                \addToTail // make sure it goes after what you are scoping
            );

	
	//~synths = "~syn" ++ "th"++ {~syns}.value.asString;
        // making noise onto the buffer
	//	~syn2;/* = SynthDef("test2", { arg bus;
	~syns;

	~scope.start;		

		
	// 		[
	// 	busses1.at(popupS_Ch1.value).value.postln;, busses2.at(popupS_Ch2.value).value.postln;

	
	// ];

				//simple compressor
		
	Button(w, Rect(250, 30, 100, 30)) // Compressor button 
        .states_([ 
                [ "Off", Color.black, Color.grey], 
                ["On", Color.new(0.6,0.2,0.2), Color.red] 
        ]) 
        .action_({ |butt| 
                if (butt.value == 1) 

	{

	~syn2 = SynthDef("compressor", { |bus, thres=0.5, effectbus, bufnum, slbelow = 10, slabove = 0.1, attack = 0.002, release = 0.15, amp = 0.5,mix = 1|
                var z, z1, z2, snd;
		//z = In.ar(bus,2);
		z1 = busses1.at(popupS_Ch1_2.value).value.postln;
		//z2 = busses2.at(popupS_Ch2.value).value.postln;		        
		snd = Compander.ar(z1, z1, thres, slbelow, slabove, attack, release, amp);
		[snd, LeakDC.ar(snd, 0.995)];
                Out.ar(0,(snd-z1)*mix);
}).play(
                RootNode(server),
                [\bus, ~bus],
                \addToTail // make sure it goes after what you are scoping
            );

		
		~syns;
	}
	{~syn2.free;}
});
//side Chain Compression
		Button(w, Rect(400, 30, 100, 30)) // Compressor button 
        .states_([ 
                [ "Off", Color.black, Color.grey], 
                ["On", Color.new(0.6,0.2,0.2), Color.red] 
        ]) 
        .action_({ |butt| 
                if (butt.value == 1) 

	{
	~syn3 = SynthDef("sideChainComp", { |bus, thres=0.5, effectbus, bufnum, slbelow = 10, slabove = 0.1, attack = 0.002, release = 0.15, amp = 0.5,mix = 1|
		var z, z1, z2, z3, z4, z5, z6, z7, z8, snd;
		//	~z1 = Array.fill(8, {In.ar(bus,2)});
		
		//	~z2 = Array.fill(8, {In.ar(bus,2)});
		//~z1 = In.ar()
		//		~z1;
		//	~z2;
		z1 = busses1.at(popupS_Ch1.value).value.postln;
		z2 = busses2.at(popupS_Ch2.value).value.postln;

	

		snd = Compander.ar(z1, z2, thres, slbelow, slabove, attack, release, amp);
		[snd, LeakDC.ar(snd, 0.995)];
		Out.ar(0,(snd-[z1,z2]))*mix;
}).play(
                RootNode(server),
                [\bus, ~bus],
                \addToTail // make sure it goes after what you are scoping
            );

		
		~syns;
	}
	{~syn3.free;}
});
		
	//Sliders_
    k = Array.fill(7, {Slider(w, Rect(0,100, 100, 50))});
    k[0].bounds_(Rect(250, 80, 88, 20)).value_(0.01);//InitialValue&&Position
    k[1].bounds_(Rect(250, 120, 88, 20)).value_(0.01);//Same
    k[2].bounds_(Rect(250, 160, 88, 20)).value_(0.01);//Same
    k[3].bounds_(Rect(250, 210, 88, 20)).value_(0.01);//Same
	k[4].bounds_(Rect(250, 250, 88, 20)).value_(0.01);//Same
	k[5].bounds_(Rect(250, 290, 88, 20)).value_(0.01);//Same
	k[6].bounds_(Rect(250, 330, 88, 20)).value_(0.01);//Same

	//threshold
    k[0].action_({
        |obj| //
        ~thresh = obj.value.linlin(0.0,1.0,0.001, 1.0).round(0.001).postln;
        ~numboxthres.value_(obj.value.linlin(0.0, 1.0, 0.001, 1.0));//GoesToNumBox
         compAction1 = 
		{
			~syn2.set(\thres, ~thresh);
			//~syn2.set(\bus, ~bus[0]);
}.value;
    });
	
	//slbelow
    k[1].action_({
        |obj| //
        ~slbelow = obj.value.linlin(0.0,1.0,0.001,20.0).round(0.001).postln;
        ~numboxslbelow.value_(obj.value.linlin(0.0, 1.0, 0.001, 20.0));//GoesToNumBox
         compAction2 = 
		{
			~syn2.set(\slbelow, ~slbelow);
			//~syn2.set(\bus, ~bus[0]);
}.value;
    
    });
	//slabove
    k[2].action_({
        |obj| //
        ~slabove = obj.value.linlin(0.0,1.0,0.001,20.0).round(0.001).postln;
        ~numboxslabove.value_(obj.value.linlin(0.0, 1.0, 0.001, 20.0));//GoesToNumBox
         compAction3 = 
		{
			~syn2.set(\slabove, ~slabove);
			//~syn2.set(\bus, ~bus[0]);
			
}.value;
    
    });
	//attack
    k[3].action_({
        |obj| //
        ~attack = obj.value.linlin(0.0,1.0,0.001,1.0).round(0.01).postln;
        ~numboxattack.value_(obj.value.linlin(0.0, 1.0, 0.001, 1.0));//GoesToNumBox
         compAction4 = 
		{
			~syn2.set(\attack, ~attack);
			//~syn2.set(\bus, ~bus[0]);
}.value;
    
    });
	//release
    k[4].action_({
        |obj| //
        ~release = obj.value.linlin (0.0, 1.0, 0.01, 6.0).round(0.01).postln;
        ~numboxrelease.value_(obj.value.linlin(0.0, 1.0, 0.01, 6.0));//GoesToNumBox
         compAction5 = 
		{
			~syn2.set(\release, ~release);
			//~syn2.set(\bus, ~bus[0]);
}.value;
    
    });

k[5].action_({
        |obj| //
        ~amp = obj.value.linlin (0.0, 1.0, 0.1, 1.0).round(0.01).postln;
        ~numboxamp.value_(obj.value.linlin(0.0, 1.0, 0.1, 1.0));//GoesToNumBox
         compAction6 = 
		{
			~syn2.set(\amp, ~amp);
			//~syn2.set(\bus, ~bus[0]);
}.value;
    
    });

	k[6].action_({
        |obj| //
        ~mix = obj.value.linlin (0.0, 1.0, 0.001, 1.0).round(0.01).postln;
        ~numboxmix.value_(obj.value.linlin(0.0, 1.0, 0.001, 1.0));//GoesToNumBox
         compAction7 = 
		{
			~syn2.set(\mix, ~mix);
			//~syn2.set(\bus, ~bus[0]);
}.value;
    
    });

	
	////////////////////////////////////////////////////////
    //Threshold NumBox
    ~numboxthres = NumberBox(w, Rect(250, 100, 50, 20))
    .value_(0.001)
    .clipLo_(0.001)
    .clipHi_(1.0)
    .font_(Font ("Times New Roman",16))
    .background_ (Color.new255(240, 240, 255))
    .decimals_(2)
    .action_({
        arg obj;
        var waw;
        waw = obj.value.linlin(0.001, 1.0, 0.001, 1.0);
        k[0].valueAction_(waw)
    });
//slopebelow NumBox
    ~numboxslbelow = NumberBox(w, Rect(250, 140, 50, 20))
    .value_(0.01)
    .clipLo_(0.001)
    .clipHi_(20.0)
    .font_(Font ("Times New Roman",16))
    .background_ (Color.new255(240, 240, 255))
    .decimals_(2)
    .action_({
        arg obj;
        var waw;
        waw = obj.value.linlin(0.001, 20.0, 0.0, 1.0);
        k[1].valueAction_(waw)
    });

	//slopeabove NumBox
    ~numboxslabove = NumberBox(w, Rect(250, 180, 50, 20))
    .value_(0.01)
    .clipLo_(0.001)
    .clipHi_(20.0)
    .font_(Font ("Times New Roman",16))
    .background_ (Color.new255(240, 240, 255))
    .decimals_(2)
    .action_({
        arg obj;
        var waw;
        waw = obj.value.linlin(0.001, 20.0, 0.0, 1.0);
        k[2].valueAction_(waw)
    });
//attack NumBox
    ~numboxattack = NumberBox(w, Rect(250, 230, 50, 20))
    .value_(0.01)
    .clipLo_(0.001)
    .clipHi_(1.0)
    .font_(Font ("Times New Roman",16))
    .background_ (Color.new255(240, 240, 255))
    .decimals_(2)
    .action_({
        arg obj;
        var waw;
        waw = obj.value.linlin(0.01, 1.0, 0.0, 1.0);
        k[3].valueAction_(waw)
    });
//release NumBox
    ~numboxrelease = NumberBox(w, Rect(250, 270, 50, 20))
    .value_(0.01)
    .clipLo_(0.01)
    .clipHi_(6.0)
    .font_(Font ("Times New Roman",16))
    .background_ (Color.new255(240, 240, 255))
    .decimals_(2)
    .action_({
        arg obj;
        var waw;
        waw = obj.value.linlin(0.001, 6.0, 0.0, 1.0);
        k[4].valueAction_(waw)
    });
	//amp NumBox
    ~numboxamp = NumberBox(w, Rect(250, 310, 50, 20))
    .value_(0.01)
    .clipLo_(0.01)
    .clipHi_(1.0)
    .font_(Font ("Times New Roman",16))
    .background_ (Color.new255(240, 240, 255))
    .decimals_(2)
    .action_({
        arg obj;
        var waw;
        waw = obj.value.linlin(0.01, 1.0, 0.0, 1.0);
        k[5].valueAction_(waw)
    });

	//mix NumBox
    ~numboxmix = NumberBox(w, Rect(250, 350, 50, 20))
    .value_(0.01)
    .clipLo_(0.001)
    .clipHi_(1.0)
    .font_(Font ("Times New Roman",16))
    .background_ (Color.new255(240, 240, 255))
    .decimals_(2)
    .action_({
        arg obj;
        var waw;
        waw = obj.value.linlin(0.001, 1.0, 0.0, 1.0);
        k[6].valueAction_(waw)
    });

	//////////////////////////////////////////////////////////////////
	
	//Record

Button(w, Rect(120, 30, 100, 30)) // Record button 
        .states_([ 
                [ "Record", Color.black, Color.new(0.6,0.2,0.2)], 
                ["Recording", Color.new(0.6,0.2,0.2), Color.red] 
        ]) 
        .action_({ |butt| 
                if (butt.value == 1) 
	
	{[
		channels.at(popupCh.value).value.postln;,

		formats.at(popupF.value).value.postln;,

		samples.at(popupS.value).value.postln;
	];
		
		~syns;    
		~currentSampleFormat.postln;
		~scope.style_(1);
		~scope.waveColors_([Color.rand,Color.rand]);
		~scope.fill = true;
		~scope.background_(Color.red(0.6, 0.8));
		~scope.start;		
		AppClock.sched(0, {fork{server.prepareForRecord(~recordPath); server.sync; server.record}})
	}
			
	{server.stopRecording; ~scope.style_(0); ~scope.background_(Color.black(0.4, 0.5));~scope.start;};




	

}); 

		w.onClose={

server.freeAll;

	};


	CmdPeriod.doOnce({w.close});
		
			//CmdPeriod.removeAll;
		//	w.front;
	}
	

}