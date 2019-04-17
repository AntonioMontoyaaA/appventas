package neto.com.mx.reporte.ui;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.view.View;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import neto.com.mx.reporte.R;
import neto.com.mx.reporte.databinding.ActivitySplashScreenBinding;
import neto.com.mx.reporte.model.login.Login;
import neto.com.mx.reporte.provider.ProviderLogin;
import neto.com.mx.reporte.utils.Util;

/**
 * Clase inicial, que muestra el splash screen
 */
public class ActivitySplashScreen extends AppCompatActivity {

	private ActivitySplashScreenBinding binding;
	private static final int TIME_TO_SHOW = 2000;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		getWindow().requestFeature(Window.FEATURE_NO_TITLE);

		binding  = DataBindingUtil.setContentView(this, R.layout.activity_splash_screen);

		View decoracion = getWindow().getDecorView();
		int uiOptions = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION | View.SYSTEM_UI_FLAG_FULLSCREEN;
		decoracion.setSystemUiVisibility(uiOptions);

		SharedPreferences preferences = getSharedPreferences("datosReporte", Context.MODE_PRIVATE);
		String usuario = preferences.getString("usuario","");
		String contra = preferences.getString("contra","");

		setAnimation();
		login(usuario, contra);

	}

	/**
	 * método que inicia los métodos para la animación del splash screen
	 */
	private void setAnimation() {
		binding.kenBurnsImages.setImageResource(R.color.colorAccent);
		animation2();
		animation3();
		blockUI();
	}

	/**
	 * animación que trae la imagen de arriba hacía el centro
	 */
	private void animation2() {
		binding.logo.setAlpha(1.0F);
		Animation anim = AnimationUtils.loadAnimation(this, R.anim.translate_top_to_center);
		binding.logo.startAnimation(anim);
	}

	/**
	 * animación que trae el texto de alfa a visible
	 */
	private void animation3() {
		ObjectAnimator alphaAnimation = ObjectAnimator.ofFloat(binding.welcomeText, "alpha", 0.0F, 1.0F);
		alphaAnimation.setStartDelay(800);
		alphaAnimation.setDuration(400);
		alphaAnimation.start();
	}

	private void goMainActivity() {
		unblockUI();
		Intent main = new Intent(ActivitySplashScreen.this, ActivityMain.class);
		ActivitySplashScreen.this.startActivity(main);
		ActivitySplashScreen.this.finish();
	}


	private void goLogin() {
		unblockUI();
		Intent login = new Intent(ActivitySplashScreen.this, ActivityLogin.class);
		ActivitySplashScreen.this.startActivity(login);
		ActivitySplashScreen.this.finish();
	}

	private void blockUI(){
		Util.addProgressBar(this, binding.container,binding.container.getChildCount()-1 );
	}

	private void unblockUI(){
		binding.container.removeViewAt(binding.container.getChildCount()-2);
	}

	public void compruebaUsuario(String usuario, String contrasena) {
		ProviderLogin.getInstance(this).getLogin(usuario, contrasena,
				new ProviderLogin.ConsultaLogin() {
					@Override
					public void resolve(Login login) {

					}
					@Override
					public void reject(Exception e) {}
				});
	}

	public void login(final String usuario, String contra){
		final Snackbar[] snackbar = new Snackbar[1];
		if(usuario!=null && !usuario.equals("")){
			compruebaUsuario(usuario, contra);
			new Handler().postDelayed(new Runnable() {
				@Override
				public void run() {
					if(Util.isOnline(ActivitySplashScreen.this)){
						goMainActivity();
					} else {
						snackbar[0] = Snackbar.make(binding.container,
								Html.fromHtml("<b><font color=\"#254581\">" + getString(R.string.internet) + "</font></b>"), Snackbar.LENGTH_LONG);
						snackbar[0].setAction(Html.fromHtml("<b><font color=\"#254581\">" + getString(R.string.reintentar)  + "</font></b>"), new View.OnClickListener() {
							@Override
							public void onClick(View v) {
								if(Util.isOnline(ActivitySplashScreen.this) && !usuario.equals("")){
									goMainActivity();
								}else{
									snackbar[0] = Snackbar.make(binding.container, Html.fromHtml("<b><font color=\"#FF20609F\">" + getString(R.string.internet) + "</font></b>"), Snackbar.LENGTH_LONG);

									snackbar[0].setAction(Html.fromHtml("<b><font color=\"#254581\">" + getString(R.string.reintentar) + "</font></b>"), new View.OnClickListener() {
										@Override
										public void onClick(View v) {
											if(Util.isOnline(ActivitySplashScreen.this) && !usuario.equals("")){
												goMainActivity();
											}else{
												snackbar[0] = Snackbar.make(binding.container, Html.fromHtml("<b><font color=\"#254581\">" + getString(R.string.reintenta) + "</font></b>"), Snackbar.LENGTH_LONG);
											}
										}
									});

									View snackBarView = snackbar[0].getView();
									snackBarView.setBackgroundColor(getResources().getColor(R.color.blanco));
									snackbar[0].show();
								}
							}
						});

						View snackBarView = snackbar[0].getView();
						snackBarView.setBackgroundColor(getResources().getColor(R.color.blanco));
						snackbar[0].show();
					}


				}
			}, TIME_TO_SHOW);
		} else {
			new Handler().postDelayed(new Runnable() {
				@Override
				public void run() {
					if(Util.isOnline(ActivitySplashScreen.this)){
						goLogin();
					} else {
						snackbar[0] = Snackbar.make(binding.container, Html.fromHtml("<b><font color=\"#254581\">" + getString(R.string.internet) + "</font></b>"), Snackbar.LENGTH_LONG);
						snackbar[0].setAction(Html.fromHtml("<b><font color=\"#254581\">" + getString(R.string.reintentar)  + "</font></b>"), new View.OnClickListener() {
							@Override
							public void onClick(View v) {
								if(Util.isOnline(ActivitySplashScreen.this)){
									goLogin();
								}else{
									snackbar[0] = Snackbar.make(binding.container, Html.fromHtml("<b><font color=\"#254581\">" + getString(R.string.internet) + "</font></b>"), Snackbar.LENGTH_LONG);
									snackbar[0].setAction(Html.fromHtml("<b><font color=\"#254581\">" + getString(R.string.reintentar) +
											"</font></b>"), new View.OnClickListener() {
										@Override
										public void onClick(View v) {
											if(Util.isOnline(ActivitySplashScreen.this)){
												goLogin();
											}else{
												snackbar[0] = Snackbar.make(binding.container,Html.fromHtml("<b><font color=\"#254581\">" + getString(R.string.reintenta) +"</font></b>"), Snackbar.LENGTH_LONG);
											}
										}
									});

									View snackBarView = snackbar[0].getView();
									snackBarView.setBackgroundColor(getResources().getColor(R.color.blanco));
									snackbar[0].show();
								}
							}
						});

						View snackBarView = snackbar[0].getView();
						snackBarView.setBackgroundColor(getResources().getColor(R.color.blanco));
						snackbar[0].show();
					}


				}
			}, TIME_TO_SHOW);
		}
	}


}
