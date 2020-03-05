class LoadBar {
	var num:Number = 1, d:Number = Math.PI * 2 / 9, i:Number, size:Number, loaded:Number;
	var clearLoading;

	function initialize():Void {
		for(i = 1; i < 10; i++) {
			_root.attachMovie("bar", "bar" + i, i);
			eval("bar" + i)._x = 500 + (50 - 3 * i) * Math.cos(d * i);
			eval("bar" + i)._y = 70 + (50 - 3 * i) * Math.sin(d * i);
			eval("bar" + i)._rotation = 40 * (i + 2.3);
			eval("bar" + i)._xscale = eval("bar" + i)._yscale *= 1 - 0.05 * i;
			eval("bar" + i)._alpha = 100 - 10 * (i - 1);
		}
		i = 1;
	}
	function startLoad():Void {
		loaded = _root.getBytesLoaded();
		_root.pct = "加载中..."+Math.floor(loaded / size * 100) + "%";
		if(loaded >= size) {
			clearInterval(clearLoading);
			trace("Finish!");
			_root.play();
			for(i = 9; i > 0; i--) {
				eval("bar" + i).removeMovieClip();
			}
		}
		if((loaded >= (size / 55 * num))) {
			num++;
			eval("bar" + i)._alpha -= 10;
			if(eval("bar" + i)._alpha <= 0) i++;
			trace(eval("bar" + i)._alpha);
		}
		trace(loaded + "/" + size + " = " + _root.pct + "\n");
	}
	function loading():Void {
		clearLoading = setInterval(this, "startLoad", 10);
	}

	function LoadBar(size:Number) {
		this.size = size;
		trace("The file size is : " + size + " byte.");
	}
}