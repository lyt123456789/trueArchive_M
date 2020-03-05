package 
{
	import flash.events.MouseEvent;
	import flash.display.MovieClip;
	import com.greensock.*;
	import com.greensock.easing.*;
	import flash.external.ExternalInterface;
	public class citys extends MovieClip
	{
		private var area:MovieClip;
		public function citys()
		{
			area = getChildByName('area') as MovieClip;
			addEventListener(MouseEvent.MOUSE_OVER,handleOver);
			addEventListener(MouseEvent.MOUSE_OUT,handleOut);
			addEventListener(MouseEvent.CLICK,handleClick);
		}
		private function handleOver(e:MouseEvent):void
		{
			var p:MovieClip=MovieClip(this.parent) as MovieClip;
			var num:int=p.numChildren;
			p.setChildIndex(this, num-2);
			showProgress();
			TweenMax.to(area, 1.25, {glowFilter:{color:0xff6600, alpha:1, blurX:15, blurY:15, strength:1.5},colorTransform:{tint:0xff0000, tintAmount:0.2}, ease:Circ.easeOut});
		}
		private function handleOut(e:MouseEvent):void
		{
			TweenMax.to(area, 1.25, {glowFilter:{color:0xff6600, alpha:0, blurX:0, blurY:0, strength:0},colorTransform:{tint:0xff0000, tintAmount:0}, ease:Circ.easeOut});
		}
		private function handleClick(e:MouseEvent):void
		{
			//trace(this.name);
			ExternalInterface.call("showAreaMsg",this.name);
		}
		private function showProgress():void{
			var _r:MovieClip= MovieClip(root as MovieClip);
			trace(this.name);
			var n=_r['pc'].(@id==this.name);
			_r.progress_mc.progress_txt.text=n.@name+': '+n.@data+' 件';
			var h=n.@data/_r['max']*200;
			TweenLite.to(_r.progress_mc.point_mc, 1, {height:h});
			TweenLite.to(_r.progress_mc.progress_txt, 1, {y:_r.progress_mc.point_mc.y-h});
		}
	}

}