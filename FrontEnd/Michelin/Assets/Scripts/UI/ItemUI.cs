using TMPro;
using UnityEngine;

public class ItemUI : MonoBehaviour
{
    public TMP_Text itemNameText;
    public TMP_Text itemPriceText;
    public TMP_Text itemDescriptionText;
    public bool itemIsPurchased;

    public void SetItem(string name, int price, string description, bool isPurchased)
    {
        itemNameText.text = name;
        itemPriceText.text = price+"³É";
        itemDescriptionText.text = description;
        itemIsPurchased = isPurchased;
    }
}
