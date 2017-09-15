package com.marvik.libs.android.net;

import android.annotation.TargetApi;
import android.content.Context;
import android.net.DhcpInfo;
import android.net.wifi.WifiConfiguration;
import android.net.wifi.WifiEnterpriseConfig;
import android.net.wifi.WifiManager;
import android.os.Build;

import java.security.cert.X509Certificate;
import java.util.Locale;

/**
 * Network utils - Handles all networks utils
 * Created by victor on 4/14/2016.
 */
public class NetworkUtils {

    private WifiManager wifiManager;

    /**
     * Turns on Wifi
     */
    public boolean wifiEnable(Context context) {
        return getWifiManager(context).isWifiEnabled()
                ? getWifiManager(context).isWifiEnabled() : getWifiManager(context).setWifiEnabled(true);
    }

    /**
     * Turns off Wifi
     */
    public boolean wifiDisable(Context context) {
        return getWifiManager(context).isWifiEnabled()
                ? getWifiManager(context).setWifiEnabled(false) : getWifiManager(context).isWifiEnabled();
    }

    /**
     * Start scanning wifi networks
     * TODO TEST THE VARIOUS TEST CASES OF THIS METHOD
     *
     * @return
     */
    public boolean wifiStartScanning(Context context) {
        return wifiEnable(context) ? getWifiManager(context).startScan() : wifiStartScanning(context);
    }

    /**
     * Return an instance of wifi manager
     *
     * @return
     */
    public WifiManager getWifiManager(Context context) {
        return wifiManager == null ? wifiManager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE) : wifiManager;
    }

    /**
     * Gets the Provider friendly name
     *
     * @param wifiConfiguration
     * @return wifiConfiguration#providerFriendlyName
     */
    public String getProviderFriendlyName(WifiConfiguration wifiConfiguration) {
        return Build.VERSION.SDK_INT >= 23 ? wifiConfiguration.providerFriendlyName : wifiConfiguration.SSID;
    }

    /**
     * The network's SSID. Can either be an ASCII string,
     * which must be enclosed in double quotation marks
     * (e.g., {@code "MyNetwork"}, or a string of
     * hex digits,which are not enclosed in quotes
     * (e.g., {@code 01a243f405}).
     *
     * @param wifiConfiguration
     * @return WifiConfiguration#SSID
     */
    public String getSSID(WifiConfiguration wifiConfiguration) {
        return wifiConfiguration.SSID;
    }

    /**
     * When set, this network configuration entry should only be used when
     * associating with the AP having the specified BSSID. The value is
     * a string in the format of an Ethernet MAC address, e.g.,
     * <code>XX:XX:XX:XX:XX:XX</code> where each <code>X</code> is a hex digit.
     *
     * @param wifiConfiguration
     * @return WifiConfiguration#BSSID
     */
    public String getBSSID(WifiConfiguration wifiConfiguration) {
        return wifiConfiguration.BSSID;
    }

    /**
     * Return fully qualified domain name
     *
     * @param wifiConfiguration
     * @return
     */
    public String getFullyQualifiedDomainName(WifiConfiguration wifiConfiguration) {
        return Build.VERSION.SDK_INT >= 21 ? wifiConfiguration.FQDN : "Not available";
    }

    /**
     * Return is hidden ssid
     *
     * @param wifiConfiguration
     * @return is hidden ssid
     */
    public boolean isHiddenSSID(WifiConfiguration wifiConfiguration) {
        return wifiConfiguration.hiddenSSID;
    }

    /**
     * Return is passpoint network
     *
     * @param wifiConfiguration
     * @return is passpoint
     */
    @TargetApi(23)
    public boolean isPassPointNetwork(WifiConfiguration wifiConfiguration) {
        return wifiConfiguration.isPasspoint();
    }

    /**
     * Returns the Wep Keys
     *
     * @param wifiConfiguration
     * @return
     */
    public String[] getWepKeys(WifiConfiguration wifiConfiguration) {
        return wifiConfiguration.wepKeys;
    }

    /**
     * Pre-shared key for use with WPA-PSK.
     * <p/>
     * When the value of this key is read, the actual key is
     * not returned, just a "*" if the key has a value, or the null
     * string otherwise.
     *
     * @param wifiConfiguration
     * @return
     */
    public String getPreKeys(WifiConfiguration wifiConfiguration) {
        return wifiConfiguration.preSharedKey;
    }

    /**
     * Get the identity
     *
     * @param enterpriseConfig
     * @return the identity
     */
    @TargetApi(18)
    public String getIdentity(WifiEnterpriseConfig enterpriseConfig) {
        return enterpriseConfig.getIdentity();
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR2)
    @Deprecated
    public String getSubjectMatch(WifiEnterpriseConfig enterpriseConfig) {
        return enterpriseConfig.getSubjectMatch();
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR2)
    public String getAltSubjectMatch(WifiEnterpriseConfig enterpriseConfig) {
        if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.LOLLIPOP_MR1)
            return getSubjectMatch(enterpriseConfig);
        return enterpriseConfig.getAltSubjectMatch();
    }

    /**
     * Get anonymous identity
     *
     * @param enterpriseConfig
     * @return
     */
    @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR2)
    public String getAnonymousIdentity(WifiEnterpriseConfig enterpriseConfig) {
        return enterpriseConfig.getAnonymousIdentity();
    }

    /**
     * Get the domain suffix match
     *
     * @param enterpriseConfig
     * @return
     */
    @TargetApi(Build.VERSION_CODES.M)
    public String getDomainSuffixMatch(WifiEnterpriseConfig enterpriseConfig) {
        return enterpriseConfig.getDomainSuffixMatch();
    }

    /**
     * Get the public land mobile network
     *
     * @param enterpriseConfig
     * @return
     */
    @TargetApi(Build.VERSION_CODES.M)
    public String getPublicLandMobileNetwork(WifiEnterpriseConfig enterpriseConfig) {
        return enterpriseConfig.getPlmn();
    }

    /**
     * Get Realm
     *
     * @param enterpriseConfig
     * @return
     */
    @TargetApi(Build.VERSION_CODES.M)
    public String getRealm(WifiEnterpriseConfig enterpriseConfig) {
        return enterpriseConfig.getRealm();
    }

    /**
     * Get  X509Certificate Certificate type
     *
     * @param x509Certificate
     * @return
     */
    public String getCertificateType(X509Certificate x509Certificate) {
        return x509Certificate.getType();
    }

    /**
     * Get the certificate version
     *
     * @param x509Certificate
     * @return version
     */
    public String getCertificateVersion(X509Certificate x509Certificate) {
        return String.format(Locale.getDefault(), "%d", x509Certificate.getVersion());
    }

    /**
     * Get the certificate issuer DN
     *
     * @param x509Certificate
     * @return issuerDN
     */
    public String getCertificateIssuerDN(X509Certificate x509Certificate) {
        return x509Certificate.getIssuerDN().getName();
    }

    /**
     * Get the subject DN
     *
     * @param x509Certificate
     * @return
     */
    public String getCertificateSubjectDN(X509Certificate x509Certificate) {
        return x509Certificate.getSubjectDN().getName();
    }

    /**
     * Get the name of the signature algorithm
     *
     * @param x509Certificate
     * @return algorithmName
     */
    public String getCertificateSignatureAlgorithmName(X509Certificate x509Certificate) {
        return x509Certificate.getSigAlgName();
    }

    /**
     * Get the certificate signature algorithm OID
     *
     * @param x509Certificate
     * @return algorithmOID
     */
    public String getCertificateSignatureAlgorithmOID(X509Certificate x509Certificate) {
        return x509Certificate.getSigAlgOID();
    }

    public DhcpInfo getDhcpInfo(Context context) {
        return getWifiManager(context).getDhcpInfo();
    }
}
